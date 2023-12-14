package com.example.tiary.category.service;

import org.springframework.transaction.annotation.Transactional;

import com.example.tiary.category.entity.Category;

public interface CategoryService {
	@Transactional(readOnly = true)
	Category readCategory(String categoryCode);
}

