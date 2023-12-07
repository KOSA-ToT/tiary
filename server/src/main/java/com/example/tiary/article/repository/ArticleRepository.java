package com.example.tiary.article.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.tiary.article.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

	List<Article> findAllByCategory_CategoryCode(String categoryCode);

	@Modifying
	@Query("update Article a set a.view = a.view +1 where a.id = :id")
	int updateViews(@Param("id")Long articleId);
}