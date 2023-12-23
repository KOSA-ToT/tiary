package com.example.tiary.article.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tiary.article.service.ArticleLikesService;

@RestController
@RequestMapping("/like")
public class LikeController {
	private final ArticleLikesService articleLikesService;

	public LikeController(ArticleLikesService articleLikesService) {
		this.articleLikesService = articleLikesService;
	}

	@GetMapping("/{user-id}")
	public ResponseEntity isArticleLike(
		@PathVariable("user-id") Long userId
	) {
		return new ResponseEntity(HttpStatus.OK);
	}
}
