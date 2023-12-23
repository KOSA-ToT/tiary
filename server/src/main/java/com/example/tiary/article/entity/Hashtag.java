package com.example.tiary.article.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Hashtag hashtag = (Hashtag) o;
		return Objects.equals(hashtagName, hashtag.hashtagName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(hashtagName);
	}

}
