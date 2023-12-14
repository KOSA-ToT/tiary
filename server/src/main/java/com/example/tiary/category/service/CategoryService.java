package com.example.tiary.category.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.example.tiary.category.entity.Category;

public interface CategoryService {


	@Transactional(readOnly = true)
	List<Category> readCategoryList();

	@Transactional(readOnly = true)
	List<Category> readAllCategory();
	@Transactional(readOnly = true)
	Category readCategory(String categoryCode);
}

