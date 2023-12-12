package com.example.batch.article.dto;

import com.example.batch.article.entity.Article;
import com.example.batch.article.entity.RelatedArticle;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class ResponseRelatedArticleDto {
	private Long articleId;
	private String title;
	private String content;
	private double relevanceScore;

	public ResponseRelatedArticleDto from(Article article)
	{
		return ResponseRelatedArticleDto.builder()
			.articleId(article.getId())
			.title(article.getTitle())
			.content(article.getContent())
			.build();
	}

	public static ResponseRelatedArticleDto of(RelatedArticle relatedArticle)
	{
		return ResponseRelatedArticleDto.builder()
			.articleId(relatedArticle.getArticle().getId())
			.title(relatedArticle.getArticle().getTitle())
			.content(relatedArticle.getArticle().getContent())
			.relevanceScore(relatedArticle.getRelevanceScore())
			.build();
	}


}
