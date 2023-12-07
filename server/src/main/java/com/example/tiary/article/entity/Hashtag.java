package com.example.tiary.article.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class Hashtag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 20, unique = true)
	private String hashtagName;

	@OneToMany(mappedBy = "hashtag")
	@ToString.Exclude
	private List<ArticleHashtag> articleHashtags = new ArrayList<>();

	public static Hashtag of(Long id, String hashtagName, List<ArticleHashtag> articleHashtags) {
		return new Hashtag(null, hashtagName, articleHashtags);
	}
	public static Hashtag of(String hashtagName) {
		return new Hashtag(null, hashtagName, null);
	}
}
