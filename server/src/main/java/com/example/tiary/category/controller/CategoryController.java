package com.example.tiary.category.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tiary.article.service.ArticleService;

@RestController
@RequestMapping("/category")
public class CategoryController {
	private final ArticleService articleService;

	public CategoryController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@GetMapping
	public ResponseEntity getArticleListFromCategory(@RequestParam("category") String categoryCode) {
		if(categoryCode != null){
			return new ResponseEntity(articleService.readArticleFromCategoryCode(categoryCode), HttpStatus.OK);
		}
		return null;
	}
}
