package com.example.tiary.article.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.tiary.article.dto.request.RequestArticleDto;
import com.example.tiary.article.dto.response.ResponseArticleDto;
import com.example.tiary.article.entity.Article;
import com.example.tiary.article.entity.ArticleHashtag;
import com.example.tiary.article.entity.ArticleImage;
import com.example.tiary.article.repository.ArticleHashtagRepository;
import com.example.tiary.article.repository.ArticleImageRepository;
import com.example.tiary.article.repository.ArticleRepository;
import com.example.tiary.article.service.ArticleService;
import com.example.tiary.article.service.HashtagService;
import com.example.tiary.category.entity.Category;
import com.example.tiary.category.service.CategoryService;
import com.example.tiary.global.exception.BusinessLogicException;
import com.example.tiary.global.exception.ExceptionCode;
import com.example.tiary.global.s3.S3UploadService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {
	private final ArticleRepository articleRepository;
	private final ArticleHashtagRepository articleHashtagRepository;
	private final ArticleImageRepository articleImageRepository;
	private final HashtagService hashtagService;
	private final CategoryService categoryService;
	private final S3UploadService s3UploadService;

	// 게시물 조회
	@Transactional(readOnly = true)
	@Override
	public List<ResponseArticleDto> readArticleList() {
		return articleRepository.findAll().stream().map(ResponseArticleDto::from).toList();
	}

	//게시물 단건 조회
	@Transactional(readOnly = true)
	@Override
	public ResponseArticleDto readArticle(Long articleId) {

		Article article = articleRepository.findById(articleId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ARTICLE_NOT_FOUND));
		updateView(article.getId());
		return ResponseArticleDto.from(article);
	}

	// 해시태그로 조회
	@Transactional(readOnly = true)
	@Override
	public List<ResponseArticleDto> readArticleFromHashtag(String hashtag) {
		List<ArticleHashtag> articleHashtag = articleHashtagRepository.findAllByHashtag_HashtagName(hashtag);
		List<ResponseArticleDto> responseArticleDtoList = new ArrayList<>();

		for (ArticleHashtag a : articleHashtag) {
			responseArticleDtoList.add(ResponseArticleDto.from(a.getArticle()));
		}

		return responseArticleDtoList;
	}

	//카테고리로 조회
	@Transactional(readOnly = true)
	@Override
	public List<ResponseArticleDto> readArticleFromCategoryCode(String categoryCode) {
		return articleRepository.findAllByCategory_CategoryCode(categoryCode)
			.stream().map(ResponseArticleDto::from).toList();

	}

	// 게시물 생성
	//TODO DB 최적화 고민
	@Transactional
	@Override
	public Article createArticle(RequestArticleDto requestArticleDto, List<MultipartFile> multipartFiles) throws
		IOException {

		Category category = categoryService.readCategory(requestArticleDto.getCategoryCode());
		Article article = articleRepository.save(requestArticleDto.toEntity(category));

		for (MultipartFile image : multipartFiles) {
			String storeName = UUID.randomUUID() + "-" + image.getOriginalFilename();
			s3UploadService.upload(image, storeName);

			ArticleImage articleImage = ArticleImage.of(storeName, article);
			articleImageRepository.save(articleImage);
		}
		hashtagService.saveHashtag(requestArticleDto, article);
		return article;
	}

	// 게시물 수정
	@Transactional
	@Override
	public Article updateArticle(Long articleId, RequestArticleDto requestArticleDto,
		List<MultipartFile> multipartFiles) throws
		IOException {
		Article article = articleRepository.findById(articleId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ARTICLE_NOT_FOUND));

		Optional.ofNullable(requestArticleDto.getTitle()).ifPresent(article::updateTitle);
		Optional.ofNullable(requestArticleDto.getContent()).ifPresent(article::updateContent);

		if (requestArticleDto.getCategoryCode() != null) {
			Category category = categoryService.readCategory(requestArticleDto.getCategoryCode());
			article.updateCategory(category);
		}

		List<ArticleImage> oldImage = articleImageRepository.findAllByArticleId(articleId);
		for (ArticleImage image : oldImage) {
			s3UploadService.deleteImage(image.getImgUrl());
		}

		for (MultipartFile image : multipartFiles) {
			String storeName = UUID.randomUUID() + "-" + image.getOriginalFilename();
			s3UploadService.upload(image, storeName);

			ArticleImage articleImage = ArticleImage.of(storeName, article);
			articleImageRepository.save(articleImage);
		}

		hashtagService.removeOldHashtag(article);
		hashtagService.updateHashtag(hashtagService.createHashtag(requestArticleDto), article);

		return articleRepository.save(article);
	}

	//게시물 삭제
	@Transactional
	@Override
	public String deleteArticle(Long articleId) {
		Article article = articleRepository.findById(articleId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ARTICLE_ALREADY_DELETE));

		List<ArticleImage> articleImage = articleImageRepository.findAllByArticleId(articleId);
		for (ArticleImage a : articleImage) {
			s3UploadService.deleteImage(a.getImgUrl());
		}
		articleRepository.delete(article);
		return "삭제 완료";
	}
	@Override
	public int updateView(Long articleId){
		return articleRepository.updateViews(articleId);
	}
}
