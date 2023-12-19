package com.example.tiary.article.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.example.tiary.article.dto.request.RequestArticleDto;
import com.example.tiary.article.dto.response.ResponseHashtagDto;
import com.example.tiary.article.entity.Article;
import com.example.tiary.article.entity.Hashtag;

public interface HashtagService {
	// 해시태그 조회 아티클의 해시태그를 기준으로
	@Transactional(readOnly = true)
	List<ResponseHashtagDto> readHashtag(String categoryName);

	// 해시태그 조회
	@Transactional(readOnly = true)
	List<ResponseHashtagDto> readAllHashtag(String categoryCode);

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
