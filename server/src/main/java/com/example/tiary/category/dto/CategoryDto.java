package com.example.tiary.category.dto;

import com.example.tiary.category.entity.Category;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class CategoryDto {

	private String categoryCode;
	private String categoryName;

	public static CategoryDto from(Category category) {
		return CategoryDto.builder()
			.categoryCode(category.getCategoryCode())
			.categoryName(category.getCategoryName())
			.build();
	}

	public Category toEntity() {
		return Category.of(
			null,
			categoryCode,
			categoryName
		);
	}
}
