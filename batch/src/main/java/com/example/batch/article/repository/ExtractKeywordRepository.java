package com.example.batch.article.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.batch.article.entity.ExtractKeyword;

public interface ExtractKeywordRepository extends JpaRepository<ExtractKeyword, Long> {
	List<ExtractKeyword> findByArticleId(Long articleId);
}