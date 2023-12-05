package com.example.tiary.article.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tiary.article.dto.request.RequestArticleDto;
import com.example.tiary.article.dto.response.ResponseArticleDto;
import com.example.tiary.article.entity.Article;
import com.example.tiary.article.entity.ArticleHashtag;
import com.example.tiary.article.entity.Hashtag;
import com.example.tiary.article.repository.ArticleHashtagRepository;
import com.example.tiary.article.repository.ArticleRepository;
import com.example.tiary.article.service.ArticleService;
import com.example.tiary.article.service.HashtagService;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {
	private final ArticleRepository articleRepository;
	private final ArticleHashtagRepository articleHashtagRepository;
	private final HashtagService hashtagService;

	public ArticleServiceImpl(ArticleRepository articleRepository, ArticleHashtagRepository articleHashtagRepository,
		HashtagService hashtagService) {
		this.articleRepository = articleRepository;
		this.articleHashtagRepository = articleHashtagRepository;
		this.hashtagService = hashtagService;
	}

	// 게시물 조회
	@Transactional(readOnly = true)
	@Override
	public List<ResponseArticleDto> readArticleList() {
		List<Article> articles = articleRepository.findAll();

		// return articles.stream()
		// 	.map(article -> ResponseArticleDto.from(article,
		// 		hashtagRepository.findAllBy(article.getId()))).toList();
		return null;
	}

	//게시물 단건 조회

	@Transactional(readOnly = true)
	@Override
	public ResponseArticleDto readArticle(Long articleId) {

		Article article = articleRepository.findById(articleId)
			.orElseThrow(() -> new EntityNotFoundException("게시물이 존재하지 않습니다."));

		// ResponseArticleDto responseArticleDto = ResponseArticleDto.from(article, hashtagRepository.findAllByArticleId(article.getId()))

		return null;
	}

	// 게시물 생성
	//TODO DB 최적화 고민
	@Transactional
	@Override
	public Article createArticle(RequestArticleDto requestArticleDto) {

		Article article = articleRepository.saveAndFlush(requestArticleDto.toEntity());
		hashtagService.saveHashtag(hashtagService.createHashtag(requestArticleDto),article);

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
