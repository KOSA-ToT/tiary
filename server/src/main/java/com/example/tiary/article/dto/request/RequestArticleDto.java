package com.example.tiary.article.dto.request;

import java.util.List;

import com.example.tiary.article.entity.Article;
import com.example.tiary.category.entity.Category;
import com.example.tiary.users.entity.Users;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class RequestArticleDto {
	@NotNull(message = "제목을 작성해 주세요.")
	private String title;
	@NotNull
	private String content;
	private String hashtag;
	private String categoryCode;

	private List<String> storeName;

	public Article toEntity(Category category, Users users) {
		return Article.of(
			null,
			title,
			content,
			0,
			category,
			users,
			null
		);
	}
}
