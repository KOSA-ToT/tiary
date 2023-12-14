package com.example.tiary.article.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class ArticleImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String imgUrl;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "article_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Article article;

	public static ArticleImage of(String imgUrl, Article article) {
		return new ArticleImage(null, imgUrl, article);
	}
}
