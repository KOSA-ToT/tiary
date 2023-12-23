package com.example.batch.article.service;

import java.util.Map;
import java.util.function.Function;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CacheService {
	//캐시로 계산
	@Cacheable(cacheNames = "tfidf", key = "#content")
	public Map<String, Double> getTfidf(String content, Function<String, Map<String, Double>> tfidfFunction) {
		return tfidfFunction.apply(content);
	}

	//캐시비우산
	@CacheEvict(cacheNames = "tfidf", key = "#content")
	public void evictTfidfCache(String content) {
	}
}
