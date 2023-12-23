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
	private Long userId;
	private String title;
	private String content;
	private int view;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	private String createdBy;
	private String email;
	private String modifiedBy;
	private List<ResponseHashtagDto> hashtagList;
	private List<String> imgPath;

	public static ResponseArticleDto from(Article article) {
		return ResponseArticleDto.builder()
			.id(article.getId())
			.userId(article.getUsers().getId())
			.title(article.getTitle())
			.content(article.getContent())
			.view(article.getView())
			.createdAt(article.getCreatedAt())
			.modifiedAt(article.getModifiedAt())
			.createdBy(article.getCreatedBy())
			.email(article.getUsers().getEmail())
			.modifiedBy(article.getModifiedBy())
			.hashtagList(article.getArticleHashtags().stream().map(ResponseHashtagDto::from).toList())
			.build();
	}

	public static ResponseArticleDto from(Article article , List<String> imgPath) {
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
			.imgPath(imgPath)
			.build();
	}
}
