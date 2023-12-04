package com.example.tiary.article.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.example.tiary.article.entity.Article;

import lombok.Builder;
import lombok.Getter;

/**
 * DTO for {@link com.example.tiary.article.entity.Article}
 */
@Builder

public record ResponseArticleDto(
	Long id,
	String title,
	String content,
	Long view,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt,
	String createdBy,
	String modifiedBy
) implements Serializable {

	public static ResponseArticleDto from(Article article) {
		return ResponseArticleDto.builder()
			.id(article.getId())
			.title(article.getTitle())
			.content(article.getContent())
			.view(article.getView())
			.createdAt(article.getCreatedAt())
			.modifiedAt(article.getModifiedAt())
			.createdBy(article.getCreatedBy())
			.modifiedBy(article.getModifiedBy())
			.build();
	}
}