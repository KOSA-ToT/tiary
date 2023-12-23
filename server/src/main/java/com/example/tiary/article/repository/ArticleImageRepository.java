package com.example.tiary.article.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tiary.article.entity.ArticleImage;

public interface ArticleImageRepository extends JpaRepository<ArticleImage, Long> {
	List<ArticleImage> findAllByArticleId(Long articleId);
}