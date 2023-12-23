package com.example.batch.article.dto;

import com.example.batch.article.entity.Article;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArticleDto {
	private Long id;
	private String content;

	public static ArticleDto of(Article article) {
		return ArticleDto.builder()
			.id(article.getId())
			.content(article.getContent())
			.build();
	}
}

