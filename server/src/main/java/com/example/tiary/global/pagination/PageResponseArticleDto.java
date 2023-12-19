package com.example.tiary.global.pagination;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Getter;

@Getter
public class PageResponseArticleDto<T> {
	private List<T> data;
	private PageInfo pageInfo;
	private List<Integer> barNumber;

	public PageResponseArticleDto(List<T> data, Page page, List<Integer> barNumber) {
		this.data = data;
		this.pageInfo = new PageInfo(page.getNumber() + 1,
			page.getSize(), page.getTotalElements(), page.getTotalPages());
		this.barNumber = barNumber;
	}

	public PageResponseArticleDto(List<T> data, Page page) {
		this.data = data;
		this.pageInfo = new PageInfo(page.getNumber() + 1,
			page.getSize(), page.getTotalElements(), page.getTotalPages());
	}
}
