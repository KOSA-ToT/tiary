package com.example.tiary.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tiary.article.entity.Article;
import com.example.tiary.article.entity.ArticleLikes;
import com.example.tiary.users.entity.Users;

public interface ArticleLikesRepository extends JpaRepository<ArticleLikes, Long> {
	void deleteArticleLikesByArticleIdAndUsersId(Long articleId, Long usersId);
}