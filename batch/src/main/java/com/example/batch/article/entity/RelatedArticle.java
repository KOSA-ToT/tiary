package com.example.batch.article.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Entity
public class RelatedArticle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "article_id")
	private Article article;

	private double relevanceScore;

	public RelatedArticle(Long id, Article article, double relevanceScore) {
		this.id = id;
		this.article = article;
		this.relevanceScore = relevanceScore;
	}

	public RelatedArticle(Long id, double relevanceScore) {
		this.id = id;
		this.relevanceScore = relevanceScore;
	}
}