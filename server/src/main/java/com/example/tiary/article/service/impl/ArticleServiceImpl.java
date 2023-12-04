package com.example.tiary.article.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tiary.article.dto.request.RequestArticleDto;
import com.example.tiary.article.dto.response.ResponseArticleDto;
import com.example.tiary.article.entity.Article;
import com.example.tiary.article.repository.ArticleRepository;
import com.example.tiary.article.service.ArticleService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ArticleServiceImpl implements ArticleService {
	private final ArticleRepository articleRepository;

	public ArticleServiceImpl(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

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

		return ResponseArticleDto.from(articleRepository.findById(articleId)
			.orElseThrow(() -> new EntityNotFoundException("게시물이 존재하지 않습니다.")));
	}

	// 게시물 생성
	@Transactional
	@Override
	public Article createArticle(RequestArticleDto requestArticleDto) {
		return articleRepository.save(requestArticleDto.toEntity());
	}

	// 게시물 수정
	@Transactional
	@Override
	public Article updateArticle(RequestArticleDto requestArticleDto, Long articleId) {
		Article article = articleRepository.findById(articleId)
			.orElseThrow(() -> new EntityNotFoundException("게시물이 존재하지 않습니다."));

		Optional.ofNullable(requestArticleDto.title()).ifPresent(article::updateTitle);
		Optional.ofNullable(requestArticleDto.content()).ifPresent(article::updateContent);
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
