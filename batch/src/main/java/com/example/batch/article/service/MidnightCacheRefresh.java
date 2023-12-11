package com.example.batch.article.service;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.batch.article.entity.Article;
import com.example.batch.article.entity.RelatedArticle;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MidnightCacheRefresh {

	private final RecommendationService recommendationService;
	private final ArticleService articleService;

	public MidnightCacheRefresh(RecommendationService recommendationService, ArticleService articleService) {
		this.recommendationService = recommendationService;
		this.articleService = articleService;
	}

	@Scheduled(cron = "0 0 0 * * *")
	public void refreshRecommendationsAtMidnight() {
		// 자정마다 추천 게시물 캐시 갱신 로직 cron = "0 0 0 * * *"
		// 1분 마다 cron = "0 * * * * *"
		log.info("스케쥴러 작동 확인 :");
		// 모든 게시물의 추천을 다시 계산하고 캐시를 업데이트
		List<Article> allArticles = articleService.getAllArticle();
		allArticles.forEach(article -> {
			List<RelatedArticle> recommendations = recommendationService.calculateAndCacheRecommendations(article.getId());
			recommendationService.cacheRecommendations(article.getId(), recommendations);
		});
	}
}
