package com.example.tiary.article.service;

import java.util.List;

import com.example.tiary.article.dto.request.RequestArticleDto;
import com.example.tiary.article.dto.response.ResponseArticleDto;
import com.example.tiary.article.entity.Article;

import jakarta.transaction.Transactional;

public interface ArticleService {
	// 게시물 조회
	List<ResponseArticleDto> readArticleList();
	ResponseArticleDto readArticle(Long articleId);
	List<ResponseArticleDto> readArticleFromHashtag(String hashtag);
	Article createArticle(RequestArticleDto requestArticleDto);
	// 게시물 수정
	@Transactional
	Article updateArticle(RequestArticleDto requestArticleDto, Long articleId);
	String deleteArticle(Long articleId);
}
