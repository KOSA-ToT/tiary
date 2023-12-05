package com.example.tiary.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tiary.article.entity.ArticleHashtag;

public interface ArticleHashtagRepository extends JpaRepository<ArticleHashtag, Long> {
}