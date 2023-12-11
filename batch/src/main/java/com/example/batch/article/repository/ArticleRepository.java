package com.example.batch.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.batch.article.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}