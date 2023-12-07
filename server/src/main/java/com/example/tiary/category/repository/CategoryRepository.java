package com.example.tiary.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tiary.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	Category findCategoryByCategoryCode(String categoryCode);
}