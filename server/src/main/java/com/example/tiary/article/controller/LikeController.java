package com.example.tiary.article.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tiary.article.service.ArticleLikesService;
import com.example.tiary.users.dto.UserDto;

@RestController
@RequestMapping("/likes")
public class LikeController {
	private final ArticleLikesService articleLikesService;

	public LikeController(ArticleLikesService articleLikesService) {
		this.articleLikesService = articleLikesService;
	}

	@GetMapping("/{article-id}")
	public ResponseEntity isArticleLike(
		@PathVariable("article-id") Long articleId,
		@AuthenticationPrincipal UserDto userDto
	) {
		System.out.println("좋아요 상태 호출 입니다.");
		Boolean result = articleLikesService.getLikeState(articleId, userDto.getUsers().getId());
		return new ResponseEntity(result,HttpStatus.OK);
	}
}
