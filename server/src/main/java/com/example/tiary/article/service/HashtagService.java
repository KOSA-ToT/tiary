package com.example.tiary.article.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.example.tiary.article.dto.request.RequestArticleDto;
import com.example.tiary.article.entity.Article;

public interface HashtagService {
	@Transactional
	List<String> createHashtag(RequestArticleDto requestArticleDto);

	@Transactional
	Boolean saveHashtag(List<String> hashtagList, Article article);
}
