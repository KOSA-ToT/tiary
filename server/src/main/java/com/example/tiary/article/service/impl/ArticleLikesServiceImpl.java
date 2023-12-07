package com.example.tiary.article.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tiary.article.entity.Article;
import com.example.tiary.article.entity.ArticleLikes;
import com.example.tiary.article.repository.ArticleLikesRepository;
import com.example.tiary.article.repository.ArticleRepository;
import com.example.tiary.article.service.ArticleLikesService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ArticleLikesServiceImpl implements ArticleLikesService {
	private final ArticleRepository articleRepository;
	private final ArticleLikesRepository articleLikesRepository;

	public ArticleLikesServiceImpl(ArticleRepository articleRepository, ArticleLikesRepository articleLikesRepository) {
		this.articleRepository = articleRepository;
		this.articleLikesRepository = articleLikesRepository;
	}

	@Transactional
	@Override
	public boolean choiceLikes(Long articleId) {
		Article article = articleRepository.findById(articleId).orElseThrow(()-> new EntityNotFoundException("게시물이 존재하지 않습니다."));
		Article articleR = articleRepository.getReferenceById(articleId);
		ArticleLikes articleLikes = new ArticleLikes(null, articleR);
		articleLikesRepository.save(articleLikes);
		return true;
	}
}
