package com.example.tiary.article.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.tiary.article.dto.request.RequestArticleDto;
import com.example.tiary.article.dto.response.ResponseArticleDto;
import com.example.tiary.article.entity.Article;
import com.example.tiary.global.pagination.PageResponseArticleDto;

public interface ArticleService {
	// 게시물 조회
	List<ResponseArticleDto> readArticleList();

	//랜덤 게시물 조회
	@Transactional(readOnly = true)
	List<ResponseArticleDto> readRandomList();

	ResponseArticleDto readArticle(Long articleId);

	List<ResponseArticleDto> readArticleFromHashtag(String hashtag);

	//카테고리로 조회
	PageResponseArticleDto<ResponseArticleDto> readArticleFromCategoryCode(String categoryCode, Pageable pageable);

	// 게시물 생성
	//TODO DB 최적화 고민
	Article createArticle(Long usersId, RequestArticleDto requestArticleDto, List<String> storeNameList) throws IOException;

	// 게시물 수정
	Article updateArticle(Long usersId, Long articleId, RequestArticleDto requestArticleDto,  List<String> storeNameList) throws
		IOException;

	String deleteArticle(Long articleId, Long usersId);

	@Transactional
	int updateView(Long articleId);

	List<ResponseArticleDto> findArticlesByUserIds(List<Long> userIds);
}