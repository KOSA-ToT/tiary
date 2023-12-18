package com.example.tiary.article.service;

import java.io.IOException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.tiary.article.dto.request.RequestArticleDto;
import com.example.tiary.article.dto.response.ResponseArticleDto;
import com.example.tiary.article.entity.Article;

public interface ArticleService {
	// 게시물 조회
	List<ResponseArticleDto> readArticleList();

	ResponseArticleDto readArticle(Long articleId);

	List<ResponseArticleDto> readArticleFromHashtag(String hashtag);

	//카테고리로 조회
	List<ResponseArticleDto> readArticleFromCategoryCode(String categoryCode);

	// 게시물 생성
	//TODO DB 최적화 고민
	Article createArticle(Long usersId, RequestArticleDto requestArticleDto, List<String> storeNameList) throws IOException;

	// 게시물 수정
	Article updateArticle(Long usersId, Long articleId, RequestArticleDto requestArticleDto,
		List<MultipartFile> multipartFiles) throws
		IOException;

	String deleteArticle(Long articleId, Long usersId);

	@Transactional
	int updateView(Long articleId);
}