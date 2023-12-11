package com.example.batch.article.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.batch.article.dto.ResponseArticleDto;
import com.example.batch.article.entity.Article;
import com.example.batch.article.entity.RelatedArticle;
import com.example.batch.article.repository.ArticleRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ArticleService {
	private final ArticleRepository articleRepository;
	private final RecommendationService recommendationService;

	public ArticleService(ArticleRepository articleRepository, RecommendationService recommendationService) {
		this.articleRepository = articleRepository;
		this.recommendationService = recommendationService;
	}

	public List<Article> getAllArticle(){
		return articleRepository.findAll();
	}

	public ResponseArticleDto getRelatedArticle(Long articleId) {

		Article article = articleRepository.findById(articleId).orElseThrow();

		List<RelatedArticle> relatedArticleList = article.getRelatedArticleList();

		return ResponseArticleDto.of(article, relatedArticleList);
	}

	public void doRecommend() {
		articleRepository.findAll().forEach(
			article -> recommendationService.calculateAndCacheRecommendations(article.getId())
		);
	}
}
