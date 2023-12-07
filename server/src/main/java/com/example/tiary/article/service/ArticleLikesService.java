package com.example.tiary.article.service;

import org.springframework.transaction.annotation.Transactional;

import com.example.tiary.article.entity.ArticleUsersForLikes;

public interface ArticleLikesService {
	@Transactional
	boolean choiceLikes(Long articleId, Long usersId);

	@Transactional
	boolean cancleLikes(Long articleId, Long usersId);
	@Transactional
	boolean deleteLikes(Long articleId);
}
