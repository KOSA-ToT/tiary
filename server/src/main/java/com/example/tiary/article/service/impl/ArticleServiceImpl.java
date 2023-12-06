package com.example.tiary.article.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tiary.article.dto.request.RequestArticleDto;
import com.example.tiary.article.dto.response.ResponseArticleDto;
import com.example.tiary.article.entity.Article;
import com.example.tiary.article.entity.ArticleHashtag;
import com.example.tiary.article.repository.ArticleHashtagRepository;
import com.example.tiary.article.repository.ArticleRepository;
import com.example.tiary.article.service.ArticleService;
import com.example.tiary.article.service.HashtagService;
import com.example.tiary.category.entity.Category;
import com.example.tiary.category.service.CategoryService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {
	private final ArticleRepository articleRepository;
	private final ArticleHashtagRepository articleHashtagRepository;
	private final HashtagService hashtagService;
	private final CategoryService categoryService;


	// 게시물 조회
	@Transactional(readOnly = true)
	@Override
	public List<ResponseArticleDto> readArticleList() {
		return articleRepository.findAll().stream().map(ResponseArticleDto::from).toList();
	}

	//게시물 단건 조회
	@Transactional(readOnly = true)
	@Override
	public ResponseArticleDto readArticle(Long articleId) {

		Article article = articleRepository.findById(articleId)
			.orElseThrow(() -> new EntityNotFoundException("게시물이 존재하지 않습니다."));

		return ResponseArticleDto.from(article);
	}

	// 해시태그로 조회
	@Transactional(readOnly = true)
	@Override
	public List<ResponseArticleDto> readArticleFromHashtag(String hashtag) {
		List<ArticleHashtag> articleHashtag = articleHashtagRepository.findAllByHashtag_HashtagName(hashtag);
		List<ResponseArticleDto> responseArticleDtoList = new ArrayList<>();

		for(ArticleHashtag a : articleHashtag){
			responseArticleDtoList.add(ResponseArticleDto.from(a.getArticle()));
		}

		return responseArticleDtoList;
	}

	//카테고리로 조회
	@Transactional(readOnly = true)
	@Override
	public List<ResponseArticleDto> readArticleFromCategoryCode(String categoryCode){
		List<ResponseArticleDto> responseArticleDtoList = articleRepository.findAllByCategory_CategoryCode(categoryCode)
			.stream().map(ResponseArticleDto::from).toList();
		return responseArticleDtoList;

	}

	// 게시물 생성
	//TODO DB 최적화 고민
	@Transactional
	@Override
	public Article createArticle(RequestArticleDto requestArticleDto) {
		Category category = categoryService.readCategory(requestArticleDto.getCategoryCode());
		Article article = articleRepository.save(requestArticleDto.toEntity(category));

		hashtagService.saveHashtag(requestArticleDto, article);

		return article;
	}

	// 게시물 수정
	@Transactional
	@Override
	public Article updateArticle(RequestArticleDto requestArticleDto, Long articleId) {
		Article article = articleRepository.findById(articleId)
			.orElseThrow(() -> new EntityNotFoundException("게시물이 존재하지 않습니다."));

		Optional.ofNullable(requestArticleDto.getTitle()).ifPresent(article::updateTitle);
		Optional.ofNullable(requestArticleDto.getContent()).ifPresent(article::updateContent);

		hashtagService.removeOldHashtag(article);
		hashtagService.updateHashtag(hashtagService.createHashtag(requestArticleDto), article);

		return articleRepository.save(article);
	}

	//게시물 삭제
	@Transactional
	@Override
	public String deleteArticle(Long articleId) {
		Article article = articleRepository.findById(articleId)
			.orElseThrow(() -> new EntityNotFoundException("게시물이 이미 삭제 되었습니다."));

		articleRepository.delete(article);
		return "삭제 완료";
	}
}
