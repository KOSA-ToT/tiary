package com.example.tiary.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tiary.article.entity.ArticleLikes;

public interface ArticleLikesRepository extends JpaRepository<ArticleLikes, Long> {
}