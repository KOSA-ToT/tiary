package com.example.tiary.article.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@Entity
public class ArticleHashtag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "article_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Article article;
	@ManyToOne
	@JoinColumn(name = "hashtag_id")
	private Hashtag hashtag;

	public static ArticleHashtag of(Article article, Hashtag hashtag) {
		return new ArticleHashtag(null, article, hashtag);
	}

	public Long getHashtagId() {
		return this.id;
	}
}
