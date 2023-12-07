package com.example.tiary.article.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.example.tiary.article.entity.Article;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class ResponseArticleDto {
	private Long id;
	private String title;
	private String content;
	private int view;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	private String createdBy;
	private String modifiedBy;
	private List<ResponseHashtagDto> hashtagList;

	public static ResponseArticleDto from(Article article) {
		return ResponseArticleDto.builder()
			.id(article.getId())
			.title(article.getTitle())
			.content(article.getContent())
			.view(article.getView())
			.createdAt(article.getCreatedAt())
			.modifiedAt(article.getModifiedAt())
			.createdBy(article.getCreatedBy())
			.modifiedBy(article.getModifiedBy())
			.hashtagList(article.getArticleHashtags().stream().map(ResponseHashtagDto::from).toList())
			.build();
	}
}
