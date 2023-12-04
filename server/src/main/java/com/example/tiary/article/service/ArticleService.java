package com.example.tiary.article.service;

import java.util.List;

import com.example.tiary.article.dto.request.RequestArticleDto;
import com.example.tiary.article.dto.response.ResponseArticleDto;
import com.example.tiary.article.entity.Article;

import jakarta.transaction.Transactional;

public interface ArticleService {
	// 게시물 조회
	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	List<ResponseArticleDto> readArticleList();

	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	ResponseArticleDto readArticle(Long articleId);

	Article createArticle(RequestArticleDto requestArticleDto);

	// 게시물 수정
	@Transactional
	Article updateArticle(RequestArticleDto requestArticleDto, Long articleId);

	@org.springframework.transaction.annotation.Transactional
	String deleteArticle(Long articleId);
}
