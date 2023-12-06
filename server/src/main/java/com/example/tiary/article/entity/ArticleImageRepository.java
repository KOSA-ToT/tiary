package com.example.tiary.article.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleImageRepository extends JpaRepository<ArticleImage, Long> {
	List<ArticleImage> findAllByArticleId(Long articleId);
}