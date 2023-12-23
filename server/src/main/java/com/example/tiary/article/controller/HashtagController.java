package com.example.tiary.article.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tiary.article.service.HashtagService;

@RestController
@RequestMapping("/hashtag")
public class HashtagController {
	private final HashtagService hashtagService;

	public HashtagController(HashtagService hashtagService) {
		this.hashtagService = hashtagService;
	}

	@GetMapping
	public ResponseEntity getHashtagContainCategoryNameFromArticle(@RequestParam String categoryName){
		return new ResponseEntity(hashtagService.readHashtag(categoryName), HttpStatus.OK);
	}
}
