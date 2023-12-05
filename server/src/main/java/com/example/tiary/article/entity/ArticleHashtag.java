package com.example.tiary.article.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ArticleHashtag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "article_id")
	private Article article;
	@ManyToOne
	@JoinColumn(name = "hashtag_id")
	private Hashtag hashtag;

	public static ArticleHashtag of(Article article, Hashtag hashtag){
		return new ArticleHashtag(null,article, hashtag);
	}
}
