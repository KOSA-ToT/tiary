package com.example.tiary.admin.dto.response;

import org.joda.time.LocalDateTime;

import com.example.tiary.article.entity.Article;
import com.example.tiary.comment.dto.response.CommentResponseDTO;
import com.example.tiary.comment.entity.Comment;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
@Getter
@ToString
@Builder
public class ResponseNoticeDto {
	private Long id;
	private String title;
	private Long categoryId;
	private String content;
	private java.time.LocalDateTime createdAt;
	private String categoryName;
	private String imgUrl;

	public static ResponseNoticeDto from(Article article) {
		return
			ResponseNoticeDto.builder()
				.id(article.getId())				.title(article.getTitle())
				.content(article.getContent())
				.createdAt(article.getCreatedAt())
				.build();
	}
}
