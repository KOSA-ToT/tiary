package com.example.tiary.category.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tiary.category.entity.Category;
import com.example.tiary.category.repository.CategoryRepository;
import com.example.tiary.category.service.CategoryService;

@Service
public class CatecoryServiceImpl implements CategoryService {
	private final CategoryRepository categoryRepository;

	public CatecoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	@Transactional(readOnly = true)
	@Override
	public List<Category> readCategoryList() {
		return categoryRepository.findAll();
	}
	@Transactional(readOnly = true)
	@Override
	public List<Category> readAllCategory(){
		return categoryRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Category readCategory(String categoryCode) {
		return categoryRepository.findCategoryByCategoryCode(categoryCode);
	}
}
