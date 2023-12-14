package com.example.batch.article.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
		List<RelatedArticle> allRelatedArticles = article.getRelatedArticleList();

		// 랜덤으로 6개의 연관 게시물 선택
		List<RelatedArticle> selectedRelatedArticles = getRandomRelatedArticles(allRelatedArticles, 6);

		return ResponseArticleDto.of(article, selectedRelatedArticles);
	}

	private List<RelatedArticle> getRandomRelatedArticles(List<RelatedArticle> allRelatedArticles, int count) {
		if (allRelatedArticles.size() <= count) {
			return allRelatedArticles; // 연관 게시물이 요청한 개수보다 적을 경우 전체 반환
		}

		List<RelatedArticle> selectedRelatedArticles = new ArrayList<>();
		Random random = new Random();

		while (selectedRelatedArticles.size() < count) {
			int randomIndex = random.nextInt(allRelatedArticles.size());
			RelatedArticle randomRelatedArticle = allRelatedArticles.get(randomIndex);

			// 중복된 게시물이 추가되지 않도록 체크
			if (!selectedRelatedArticles.contains(randomRelatedArticle)) {
				selectedRelatedArticles.add(randomRelatedArticle);
			}
		}

		return selectedRelatedArticles;
	}
	public void doRecommend() {
		articleRepository.findAll().forEach(
			article -> recommendationService.calculateAndCacheRecommendations(article.getId())
		);
	}
}
