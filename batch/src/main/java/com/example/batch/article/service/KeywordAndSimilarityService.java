package com.example.batch.article.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.batch.article.entity.Article;
import com.example.batch.article.entity.RelatedArticle;
import com.example.batch.article.repository.ArticleRepository;

@Service
public class KeywordAndSimilarityService {
	private final ArticleRepository articleRepository;

	public KeywordAndSimilarityService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}


	//TF-IDF 계산
	@Cacheable(cacheNames = "tfidf", key = "#content")
	public Map<String, Double> calculateTFIDF(String content) {
		String[] words = content.split("\\s+");

		// Calculate TF
		Map<String, Integer> termFrequency = Arrays.stream(words)
			.collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(e -> 1)));

		// Calculate IDF
		Map<String, Integer> documentFrequency = termFrequency.keySet().stream()
			.collect(Collectors.toMap(
				Function.identity(),
				word -> (int) articleRepository.findAll().stream()
					.map(article -> Optional.ofNullable(article.getContent()).orElse(""))
					.filter(contentText -> contentText.contains(word))
					.count()
			));

		int totalDocuments = articleRepository.findAll().size();

		return termFrequency.entrySet().stream()
			.collect(Collectors.toMap(
				Map.Entry::getKey,
				entry -> {
					double tf = (double) entry.getValue() / words.length;
					double idf = Math.log((double) totalDocuments / (1 + documentFrequency.getOrDefault(entry.getKey(), 0)));
					return tf * idf;
				}
			));
	}

	//코사인 유사도로 계산할 때
	public double calculateCosineSimilarity(String content1, String content2) {
		Map<String, Double> tfidf1 = calculateTFIDF(content1);
		Map<String, Double> tfidf2 = calculateTFIDF(content2);

		double dotProduct = 0;
		for (String word : tfidf1.keySet()) {
			dotProduct += tfidf1.getOrDefault(word, 0.0) * tfidf2.getOrDefault(word, 0.0);
		}

		double magnitude1 = Math.sqrt(tfidf1.values().stream().mapToDouble(value -> value * value).sum());
		double magnitude2 = Math.sqrt(tfidf2.values().stream().mapToDouble(value -> value * value).sum());

		double similarity = dotProduct / (magnitude1 * magnitude2);
		double similarityThreshold = 0.5;

		return similarity >= similarityThreshold ? similarity : 0.0;
	}

	//단어 빈도수 계산
	public Map<String, Integer> calculateWordFrequencies() {
		List<Article> articles = articleRepository.findAll();
		int totalPosts = articles.size();

		return articles.stream()
			.map(Article::getContent)
			.flatMap(content -> Arrays.stream(content.split("\\s+")))
			.collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(e -> 1)))
			.entrySet().stream()
			.filter(entry -> entry.getValue() <= totalPosts * 0.6)
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	//연관도 계산
	public RelatedArticle calculateAssociationScore(Article article, Map<String, Double> targetTFIDF,
		Map<String, Integer> wordFrequencies) {
		float score = 0;
		// content가 null이면 에러가 나서 해결하기 위한 방어코드
		// content 는 @NotNull 이기 때문에 날 수 없긴 합니다.
		String[] words = Optional.ofNullable(article.getContent())
			.map(content -> content.split("\\s+"))
			.orElse(new String[0]);

		int totalWords = words.length;
		int rareWordCount = 0;

		for (String word : words) {
			float wordFrequency = wordFrequencies.getOrDefault(word, 0);
			double targetTFIDFValue = targetTFIDF.getOrDefault(word, 0.0);

			if (wordFrequency / totalWords <= 0.6 && wordFrequency > 0) {
				rareWordCount++;
				score += targetTFIDFValue > 0 ? 1 : 0;
			}

			if (wordFrequency / totalWords <= 0.4 && wordFrequency > 0) {
				score += targetTFIDFValue > 0 ? 1 + wordFrequency / totalWords : 0;
			}
		}

		score *= 1 + (float) rareWordCount / totalWords;
		return new RelatedArticle(article.getId(), article, score);
	}
}

