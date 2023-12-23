package com.example.tiary.article.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.tiary.article.entity.Article;
import com.example.tiary.article.entity.ArticleHashtag;
import com.example.tiary.article.entity.Hashtag;

public interface ArticleHashtagRepository extends JpaRepository<ArticleHashtag, Long> {
	List<ArticleHashtag> findAllByArticle(Article article);

	List<ArticleHashtag> findAllByHashtag_HashtagName(String hashtagName);

	@Query("SELECT DISTINCT h FROM Article a JOIN a.articleHashtags ah JOIN ah.hashtag h JOIN a.category c WHERE c.categoryName = :categoryName")
	List<Hashtag> findDistinctHashtagsByCategoryName(@Param("categoryName") String categoryName);

}