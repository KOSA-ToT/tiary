package com.example.tiary.article.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tiary.article.entity.Article;
import com.example.tiary.article.entity.ArticleHashtag;

public interface ArticleHashtagRepository extends JpaRepository<ArticleHashtag, Long> {
	List<ArticleHashtag> findAllByArticle(Article article);

	List<ArticleHashtag> findAllByHashtag_HashtagName(String hashtagName);
}