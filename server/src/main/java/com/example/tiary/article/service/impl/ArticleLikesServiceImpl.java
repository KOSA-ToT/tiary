package com.example.tiary.article.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tiary.article.entity.Article;
import com.example.tiary.article.entity.ArticleLikes;
import com.example.tiary.article.entity.ArticleUsersForLikes;
import com.example.tiary.article.repository.ArticleLikesRepository;
import com.example.tiary.article.repository.ArticleRepository;
import com.example.tiary.article.service.ArticleLikesService;
import com.example.tiary.global.exception.BusinessLogicException;
import com.example.tiary.global.exception.ExceptionCode;
import com.example.tiary.users.entity.Users;
import com.example.tiary.users.repository.UsersRepository;

@Service
public class ArticleLikesServiceImpl implements ArticleLikesService {
	private final ArticleRepository articleRepository;
	private final ArticleLikesRepository articleLikesRepository;
	private final UsersRepository usersRepository;

	public ArticleLikesServiceImpl(ArticleRepository articleRepository, ArticleLikesRepository articleLikesRepository,
		UsersRepository usersRepository) {
		this.articleRepository = articleRepository;
		this.articleLikesRepository = articleLikesRepository;
		this.usersRepository = usersRepository;
	}

	@Transactional(readOnly = true)
	@Override
	public boolean getLikeState(Long articleId, Long usersId) {
		boolean result = false;
		Users users = usersRepository.findById(usersId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
		Article article = articleRepository.findById(articleId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ARTICLE_NOT_FOUND));

		ArticleLikes articleLikes = articleLikesRepository.findArticleLikesByArticleUsersForLikes_ArticleIdAndArticleUsersForLikes_UsersId(
			article.getId(), users.getId());
		if (articleLikes != null)
			result = true;
		return result;
	}

	@Transactional
	@Override
	public boolean choiceLikes(Long articleId, Long usersId) {
		Users users = usersRepository.findById(usersId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
		Article article = articleRepository.findById(articleId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ARTICLE_NOT_FOUND));

		ArticleUsersForLikes articleUsersForLikes = ArticleUsersForLikes.of(users.getId(), article.getId());
		ArticleLikes articleLikes = ArticleLikes.of(articleUsersForLikes);

		articleLikesRepository.save(articleLikes);
		return true;
	}

	@Transactional
	@Override
	public boolean cancleLikes(Long articleId, Long usersId) {
		Users users = usersRepository.findById(usersId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
		Article article = articleRepository.findById(articleId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ARTICLE_NOT_FOUND));
		articleLikesRepository.deleteArticleLikesByArticleUsersForLikes(
			ArticleUsersForLikes.of(users.getId(), article.getId()));
		return true;
	}

	@Transactional
	@Override
	public boolean deleteLikes(Long articleId) {
		articleLikesRepository.deleteAllByArticleUsersForLikes_ArticleId(articleId);
		return true;
	}
}
