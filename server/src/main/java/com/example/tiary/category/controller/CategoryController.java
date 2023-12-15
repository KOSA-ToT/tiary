package com.example.tiary.category.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tiary.article.service.ArticleService;
import com.example.tiary.category.entity.Category;
import com.example.tiary.category.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
	private final ArticleService articleService;
	private final CategoryService categoryService;

	public CategoryController(ArticleService articleService, CategoryService categoryService) {
		this.articleService = articleService;
		this.categoryService = categoryService;
	}

	@GetMapping

	public ResponseEntity getArticleListFromCategory(@RequestParam(value = "categoryCode" , required = false) String categoryCode) {
		if (categoryCode != null) {
			return new ResponseEntity(articleService.readArticleFromCategoryCode(categoryCode), HttpStatus.OK);
		}
		List<Category> categoryList;
		categoryList = categoryService.readCategoryList();
		return new ResponseEntity<>(categoryList,HttpStatus.OK);
	}
}
