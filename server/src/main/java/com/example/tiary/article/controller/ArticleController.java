package com.example.tiary.article.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tiary.article.dto.request.RequestArticleDto;
import com.example.tiary.article.service.ArticleService;

@RestController
@RequestMapping("/article")
public class ArticleController {

	private final ArticleService articleService;

	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

	//게시물 리스트 조회
	@GetMapping
	public ResponseEntity getArticleList(@RequestParam(value = "hashtag", required = false) String hashtag) {

		if(hashtag != null){
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
	@PostMapping
	public ResponseEntity postArticle(@RequestBody RequestArticleDto requestArticleDto) {
		return new ResponseEntity<>(articleService.createArticle(requestArticleDto), HttpStatus.CREATED);
	}

	@PatchMapping("/{article-id}")
	public ResponseEntity patchArticle(@PathVariable("article-id") Long articleId,
		@RequestBody RequestArticleDto requestArticleDto) {
		return new ResponseEntity<>(articleService.updateArticle(requestArticleDto, articleId),
			HttpStatus.RESET_CONTENT);
	}

	//게시물 삭제
	@DeleteMapping("/{article-id}")
	public ResponseEntity deleteArticle(@PathVariable("article-id") Long articleId) {
		return new ResponseEntity<>(articleService.deleteArticle(articleId), HttpStatus.RESET_CONTENT);
	}
}
