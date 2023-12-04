package com.example.tiary.article.dto.request;

import java.io.Serializable;

import com.example.tiary.article.entity.Article;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * DTO for {@link com.example.tiary.article.entity.Article}
 */
@Builder
public record RequestArticleDto(
	@NotNull(message = "제목을 작성해 주세요.") String title,
	@NotNull String content)

	implements Serializable {

	public Article toEntity() {
		return Article.of(
			null,
			title,
			content,
			1L
		);
	}
}