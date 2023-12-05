package com.example.tiary.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tiary.article.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}