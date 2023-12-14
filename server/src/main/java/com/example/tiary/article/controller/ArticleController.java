package com.example.tiary.article.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.tiary.article.dto.request.RequestArticleDto;
import com.example.tiary.article.service.ArticleLikesService;
import com.example.tiary.article.service.ArticleService;
import com.example.tiary.users.dto.UserDto;

@RestController
@RequestMapping("/article")
public class ArticleController {

	private final ArticleService articleService;
	private final ArticleLikesService articleLikesService;

	public ArticleController(ArticleService articleService, ArticleLikesService articleLikesService) {
		this.articleService = articleService;
		this.articleLikesService = articleLikesService;
	}

	//게시물 리스트 조회
	@GetMapping
	public ResponseEntity getArticleList(@RequestParam(value = "hashtag", required = false) String hashtag) {

		if (hashtag != null) {
			return new ResponseEntity(articleService.readArticleFromHashtag(hashtag), HttpStatus.OK);
		}
		return new ResponseEntity<>(articleService.readArticleList(), HttpStatus.OK);
	}

	//게시물 단건 조회
	@GetMapping("/{article-id}")
	public ResponseEntity getArticle(@PathVariable("article-id") Long id) {
		return new ResponseEntity<>(articleService.readArticle(id), HttpStatus.OK);
	}

	//게시물 등록
	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity postArticle(
		@RequestPart("requestArticleDto") RequestArticleDto requestArticleDto,
		@RequestPart(value = "images", required = false) List<MultipartFile> multipartFileList,
		@AuthenticationPrincipal UserDto user) throws
		IOException {

		return new ResponseEntity<>(articleService.createArticle(user.getUsers().getId(), requestArticleDto, multipartFileList),
			HttpStatus.CREATED);
	}

	//게시물 수정
	@PatchMapping(value = "/{article-id}", consumes = {MediaType.APPLICATION_JSON_VALUE,
		MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity patchArticle(@PathVariable("article-id") Long articleId,
		@RequestPart("requestArticleDto") RequestArticleDto requestArticleDto,
		@RequestPart(value = "images", required = false) List<MultipartFile> multipartFileList,
		@AuthenticationPrincipal UserDto user) throws IOException {
		return new ResponseEntity<>(articleService.updateArticle(user.getUsers().getId(), articleId, requestArticleDto, multipartFileList),
			HttpStatus.OK);
	}

	//게시물 삭제
	@DeleteMapping("/{article-id}")
	public ResponseEntity deleteArticle(@PathVariable("article-id") Long articleId) {
		return new ResponseEntity<>(articleService.deleteArticle(articleId), HttpStatus.RESET_CONTENT);
	}

	@GetMapping("/{article-id}/likes")
	public ResponseEntity pushLikesArticle(@PathVariable("article-id") Long articleId,
		@AuthenticationPrincipal UserDto users,
		@RequestParam("check") String check) {

		if (check.equals("likes")) {
			articleLikesService.choiceLikes(articleId, users.getUsers().getId());
		} else {
			articleLikesService.cancleLikes(articleId, users.getUsers().getId());
		}
		return new ResponseEntity(HttpStatus.OK);
	}
}
