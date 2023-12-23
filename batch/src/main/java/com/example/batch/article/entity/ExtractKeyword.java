package com.example.batch.article.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class ExtractKeyword {
	@Id
	private Long articleId;

	@Column(columnDefinition = "TEXT")
	private String keywords;

	public ExtractKeyword(Long articleId, String keywords) {
		this.articleId = articleId;
		this.keywords = keywords;
	}
}
