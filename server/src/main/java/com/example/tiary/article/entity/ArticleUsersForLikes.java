package com.example.tiary.article.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Embeddable
public class ArticleUsersForLikes implements Serializable {
	@Column(name = "users_id")
	private Long usersId;

	@Column(name = "article_id")
	private Long articleId;

	public static ArticleUsersForLikes of(Long usersId, Long articleId){
		return new ArticleUsersForLikes(usersId, articleId);
	}
}
