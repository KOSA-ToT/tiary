package com.example.batch.article.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.batch.article.dto.ResponseArticleDto;
import com.example.batch.article.service.ArticleService;
import com.example.batch.article.service.ExtractKeywordService;
import com.example.batch.article.service.RecommendationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/batch")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class ArticleController {
	private final ArticleService articleService;
	private final RecommendationService recommendationService;
	private final ExtractKeywordService extractKeywordService;


	@GetMapping("/recommendations/{article-id}")
	public ResponseEntity getRelatedPost(@PathVariable("article-id") Long articleId){
		extractKeywordService.calculateAndSaveKeywords(articleId);
		recommendationService.calculateAndCacheRecommendations(articleId);

		ResponseArticleDto article = articleService.getRelatedArticle(articleId);
		return new ResponseEntity(article, HttpStatus.OK);
	}

	@PostMapping("/updateRecommendations/{article-id}")
	public ResponseEntity updateRelatedPost(@PathVariable("article-id") Long articleId){
		return null;
	}
	@GetMapping("/do")
	public void doRecommend(){
		extractKeywordService.calculateAndSaveKeywords();
	}
}
