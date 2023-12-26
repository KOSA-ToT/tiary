package com.example.tiary.article.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.example.tiary.article.service.ArticleLikesService;
import com.example.tiary.article.service.ArticleService;
import com.example.tiary.article.service.HashtagService;
import com.example.tiary.category.entity.Category;
import com.example.tiary.category.service.CategoryService;
import com.example.tiary.global.batch.BatchService;
import com.example.tiary.global.exception.BusinessLogicException;
import com.example.tiary.global.exception.ExceptionCode;
import com.example.tiary.global.pagination.PageResponseArticleDto;
import com.example.tiary.global.pagination.PaginationService;
import com.example.tiary.global.s3.service.S3UploadService;
import com.example.tiary.users.entity.Users;
import com.example.tiary.users.repository.UsersRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {
	private final ArticleRepository articleRepository;
	private final UsersRepository usersRepository;
	private final ArticleHashtagRepository articleHashtagRepository;
	private final ArticleImageRepository articleImageRepository;
	private final HashtagService hashtagService;
	private final CategoryService categoryService;
	private final S3UploadService s3UploadService;
	private final ArticleLikesService articleLikesService;
	private final BatchService batchService;
	private final PaginationService paginationService;

	// 게시물 조회
	@Transactional(readOnly = true)
	@Override
	public List<ResponseArticleDto> readArticleList() {
		List<Article> articles = articleRepository.findAll();
		// Article과 이미지 경로를 조합하여 ResponseArticleDto 리스트 생성
		return getResponseArticleDtoAddImages(articles);
	}

	//랜덤 게시물 조회
	@Transactional(readOnly = true)
	@Override
	public List<ResponseArticleDto> readRandomList() {
		List<Article> articles = articleRepository.findAllByCategoryIsNotNull();
		return new Random().ints(0, articles.size())
			.distinct()
			.limit(6)
			.mapToObj(index -> getResponseArticleDtoWithImages(articles.get(index)))
			.collect(Collectors.toList());
	}

	//게시물 단건 조회
	@Transactional
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
	public PageResponseArticleDto<ResponseArticleDto> readArticleFromCategoryCode(String categoryName,
		Pageable pageable) {
		Page<Article> articles = articleRepository.findAllByCategory_CategoryName(categoryName,
			pageable);
		List<ResponseArticleDto> responseArticleDtoList = getResponseArticleDtoAddImages(
			articles.getContent());
		List<Integer> barNumber = paginationService.getPaginationBarNumbers(pageable.getPageNumber(),
			articles.getTotalPages());

		// Article과 이미지 경로를 조합하여 ResponseArticleDto 리스트 생성
		return new PageResponseArticleDto<>(responseArticleDtoList,articles,barNumber);
	}

	//게시물에 이미지를 더하는 메서드
	private List<ResponseArticleDto> getResponseArticleDtoAddImages(List<Article> articles) {
		return articles.stream()
			.map(this::getResponseArticleDtoWithImages)
			.toList();
	}

	private ResponseArticleDto getResponseArticleDtoWithImages(Article article) {
		List<String> articleImgPaths = articleImageRepository.findAllByArticleId(article.getId())
			.stream()
			.map(this::getS3UrlForArticleImage)
			.toList();

		return ResponseArticleDto.from(article, articleImgPaths);
	}

	private String getS3UrlForArticleImage(ArticleImage articleImage) {
		try {
			return s3UploadService.getS3URL(articleImage.getImgUrl());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	// 게시물 생성
	//TODO DB 최적화 고민
	@Transactional
	@Override
	public Article createArticle(Long usersId, RequestArticleDto requestArticleDto, List<String> storeNameList) throws
		IOException {
		Users user = usersRepository.findById(usersId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
		Category category = categoryService.readCategory(requestArticleDto.getCategoryCode());
		Article article = articleRepository.save(requestArticleDto.toEntity(category, user));

		if (storeNameList != null) {
			for (String storeName : storeNameList) {
				ArticleImage articleImage = ArticleImage.of(storeName, article);
				articleImageRepository.save(articleImage);
			}
		}

		hashtagService.saveHashtag(requestArticleDto, article);
		batchService.updateRecommendationsAsync(article.getId());
		return article;
	}

	// 게시물 수정
	@Transactional
	@Override
	public Article updateArticle(Long usersId, Long articleId, RequestArticleDto requestArticleDto, List<String> storeNameList ) throws IOException {
		Users user = usersRepository.findById(usersId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
		Article article = articleRepository.findById(articleId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ARTICLE_NOT_FOUND));

		if (!Objects.equals(user.getId(), article.getUsers().getId())) {
			throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
		}

		Optional.ofNullable(requestArticleDto.getTitle()).ifPresent(article::updateTitle);
		Optional.ofNullable(requestArticleDto.getContent()).ifPresent(article::updateContent);

		if (storeNameList != null) {
			for (String storeName : storeNameList) {
				ArticleImage articleImage = ArticleImage.of(storeName, article);
				articleImageRepository.save(articleImage);
			}
		}

		if (requestArticleDto.getCategoryCode() != null) {
			Category category = categoryService.readCategory(requestArticleDto.getCategoryCode());
			article.updateCategory(category);
		}

		updateArticleHashtags(requestArticleDto, article);
		batchService.updateRecommendationsAsync(article.getId());

		return articleRepository.save(article);
	}

	//게시물 삭제
	@Transactional
	@Override
	public String deleteArticle(Long articleId, Long usersId) {
		Users users = usersRepository.findById(usersId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
		Article article = articleRepository.findById(articleId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ARTICLE_ALREADY_DELETE));
		if(!Objects.equals(article.getUsers().getId(), users.getId())){
			throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
		}
		articleImageRepository.findAllByArticleId(articleId)
			.forEach(img -> s3UploadService.deleteImage(img.getImgUrl()));
		articleLikesService.deleteLikes(articleId);
		articleRepository.deleteArticleByIdAndUsersId(articleId,usersId);
		return "삭제 완료";
	}

	@Override
	public int updateView(Long articleId) {
		return articleRepository.updateViews(articleId);
	}

	private void uploadArticleImages(Long articleId, List<MultipartFile> multipartFiles) throws IOException {
		List<ArticleImage> oldImage = articleImageRepository.findAllByArticleId(articleId);
		for (ArticleImage image : oldImage) {
			s3UploadService.deleteImage(image.getImgUrl());
		}

		for (MultipartFile image : multipartFiles) {
			String storeName = UUID.randomUUID() + "-" + image.getOriginalFilename();
			s3UploadService.upload(image, storeName);

			ArticleImage articleImage = ArticleImage.of(storeName, articleRepository.findById(articleId)
				.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ARTICLE_NOT_FOUND)));
			articleImageRepository.save(articleImage);
		}
	}

	private void updateArticleHashtags(RequestArticleDto requestArticleDto, Article article) {
		hashtagService.removeOldHashtag(article);
		hashtagService.updateHashtag(hashtagService.createHashtag(requestArticleDto), article);
	}
	@Override
	public List<ResponseArticleDto> findArticlesByUserIds(List<Long> userIds) {
		List<Article> articles = articleRepository.findByUsersIdInOrderByCreatedAtDesc(userIds);
		// 각 Article을 ResponseArticleDto로 매핑
//		List<ResponseArticleDto> result = articles.stream()
//				.map(ResponseArticleDto::from)
//				.collect(Collectors.toList());
		return getResponseArticleDtoAddImages(articles);
	}
}