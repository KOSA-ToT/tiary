package com.example.tiary.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tiary.article.entity.Article;
import com.example.tiary.article.entity.ArticleLikes;
import com.example.tiary.article.entity.ArticleUsersForLikes;
import com.example.tiary.users.entity.Users;

public interface ArticleLikesRepository extends JpaRepository<ArticleLikes, ArticleUsersForLikes> {
	void deleteArticleLikesByArticleUsersForLikes(ArticleUsersForLikes articleUsersForLikesId);

	void deleteAllByArticleUsersForLikes_ArticleId(Long articleId);

	ArticleLikes findArticleLikesByArticleUsersForLikes_ArticleIdAndArticleUsersForLikes_UsersId(Long articleId, Long usersId);
}