package com.example.tiary.article.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.tiary.article.entity.Article;
import com.example.tiary.myPage.dto.response.ResponseMyArticleDto;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

	Page<Article> findAllByCategory_CategoryName(String categoryName, Pageable pageable);

	void deleteArticleByIdAndUsersId(Long articleId, Long usersId);

	@Modifying
	@Query("update Article a set a.view = a.view +1 where a.id = :id")
	int updateViews(@Param("id") Long articleId);

	Page<Article> findAllArticleByUsersId(Long userId, Pageable pageable);

	int countByUsersId(Long userId);

	List<Article> findByUsersIdInOrderByCreatedAtDesc(List<Long> userIds);

	List<Article> findAllByCategoryIsNotNull();
}