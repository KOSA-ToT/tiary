package com.example.batch.article.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.batch.article.entity.Article;
import com.example.batch.article.entity.ExtractKeyword;
import com.example.batch.article.repository.ArticleRepository;
import com.example.batch.article.repository.ExtractKeywordRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ExtractKeywordService {
	private final ArticleRepository articleRepository;
	private final ExtractKeywordRepository extractKeywordRepository;

	public ExtractKeywordService(ArticleRepository articleRepository,
		ExtractKeywordRepository extractKeywordRepository) {
		this.articleRepository = articleRepository;
		this.extractKeywordRepository = extractKeywordRepository;
	}

	public void calculateAndSaveKeywords(Long articleId , String title, String content){
		String keywords = extractAndProcessKeyword(title + " " + content);
		ExtractKeyword extractKeyword = new ExtractKeyword(articleId, keywords);
		extractKeywordRepository.save(extractKeyword);
	}

	public void calculateAndSaveKeywords() {
		List<Article> articleList = articleRepository.findAll();
		articleList.forEach(article -> {
			String keywords = extractAndProcessKeyword(article.getTitle() + " " + article.getContent());
			ExtractKeyword extractKeyword = new ExtractKeyword(article.getId(), keywords);
			extractKeywordRepository.save(extractKeyword);
		});
	}

	public String extractAndProcessKeyword(String content) {
		List<String> extractKeywords = extractKeyword(content);
		List<String> processedKeywords = processKeywords(extractKeywords);

		return String.join(" ", processedKeywords);
	}

	private List<String> extractKeyword(String content) {
		return List.of(content.split("\\s+"));
	}

	private List<String> processKeywords(List<String> keywords) {
		return keywords;
	}
}
