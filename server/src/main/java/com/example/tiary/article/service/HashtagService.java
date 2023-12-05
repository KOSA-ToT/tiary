package com.example.tiary.article.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.example.tiary.article.dto.request.RequestArticleDto;
import com.example.tiary.article.entity.Article;
import com.example.tiary.article.entity.Hashtag;

public interface HashtagService {
	@Transactional
	List<String> createHashtag(RequestArticleDto requestArticleDto);

	//해시태그 저장
	@Transactional
	Boolean saveHashtag(RequestArticleDto requestArticleDto, Article article);

	@Transactional
	Boolean updateHashtag(List<String> hashList, Article article);

	@Transactional
	void removeOldHashtag(Article article);
}
