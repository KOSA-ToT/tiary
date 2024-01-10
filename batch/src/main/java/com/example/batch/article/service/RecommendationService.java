package com.example.batch.article.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.batch.article.entity.Article;
import com.example.batch.article.entity.ExtractKeyword;
import com.example.batch.article.entity.RelatedArticle;
import com.example.batch.article.repository.ExtractKeywordRepository;
import com.example.batch.article.repository.ArticleRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecommendationService {

	private final CacheManager cacheManager;
	private final ArticleRepository articleRepository;
	private final ExtractKeywordRepository extractKeywordRepository;
	private final KeywordAndSimilarityService keywordAndSimilarityService;

	private static final String RECOMMENDATIONS_CACHE_NAME = "recommendations";

	public RecommendationService(CacheManager cacheManager, ArticleRepository articleRepository,
		ExtractKeywordRepository extractKeywordRepository, KeywordAndSimilarityService keywordAndSimilarityService) {
		this.cacheManager = cacheManager;
		this.articleRepository = articleRepository;
		this.extractKeywordRepository = extractKeywordRepository;
		this.keywordAndSimilarityService = keywordAndSimilarityService;
	}

	@Cacheable(cacheNames = RECOMMENDATIONS_CACHE_NAME, key = "#articleId")
	public List<RelatedArticle> getRecommendationsFromCache(Long articleId) {
		return getRecommendationsDirectlyFromCache(articleId);
	}

	@CacheEvict(cacheNames = RECOMMENDATIONS_CACHE_NAME, key = "#aritcleId")
	public void evictRecommendationsCache(Long articleId) {
		// 호출하면 알아서 작동하는 메서드
	}

	public List<RelatedArticle> calculateAndCacheRecommendations(Long articleId){
		List<RelatedArticle> recommendations = getRecommendations(articleId);
		cacheRecommendations(articleId, recommendations);
		return recommendations;
	}
	private List<RelatedArticle> getRecommendationsDirectlyFromCache(Long articleId) {
		log.info("Fetching recommendations directly from cache for articleId: {}", articleId);
		return Optional.ofNullable(cacheManager.getCache(RECOMMENDATIONS_CACHE_NAME))
			.map(cache -> cache.get(articleId, List.class))
			.orElseGet(() -> {
				log.info("Cache miss. Fetching recommendations from the original logic.");
				return getRecommendations(articleId);
			});
	}

	private List<RelatedArticle> getRecommendations(Long articleId){
		Article articleOrigin = articleRepository.findById(articleId)
			.orElseThrow(() -> new EntityNotFoundException("게시물이 존재하지 않습니다"));
		String keywords = extractKeywordRepository.findById(articleId)
			.map(ExtractKeyword::getKeywords)
			.orElse("");

		Map<String, Double> targetTFIDF = keywordAndSimilarityService.calculateTFIDF(keywords);

		List<RelatedArticle> recommendations = articleRepository.findAll().stream()
			.filter(article -> !article.getId().equals(articleId))
			.map(article -> keywordAndSimilarityService.calculateAssociationScore(article, targetTFIDF,
				getWordFrequencies(article)))
			.filter(relatedArticle -> relatedArticle.getRelevanceScore() > 0)
			.sorted(Comparator.comparing(RelatedArticle::getRelevanceScore).reversed())
			.collect(Collectors.toList());

		articleOrigin.setRelatedArticleList(recommendations);
		return recommendations;
	}

	void cacheRecommendations(Long articleId, List<RelatedArticle> relatedArticles){
		// 호출만 해도 알아서 작동하는 메서드
	}

	// 게시물의 단어 빈도수를 가져오는 추가 메서드
	private Map<String, Integer> getWordFrequencies(Article article) {
		// 게시물의 단어 빈도수를 계산하는 로직을 구현
		// AssociationCalculator와 유사한 방법을 사용할 수 있습니다
		Map<String, Integer> wordFrequencies = new HashMap<>();
		String[] words = Optional.ofNullable(article.getContent())
			.map(content -> content.split("\\s+"))
			.orElse(new String[0]);
		for (String word : words) {
			wordFrequencies.put(word, wordFrequencies.getOrDefault(word, 0) + 1);
		}
		return wordFrequencies;
	}
}
