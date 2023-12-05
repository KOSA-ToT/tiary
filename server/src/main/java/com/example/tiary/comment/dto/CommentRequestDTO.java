package com.example.tiary.comment.dto;

import com.example.tiary.article.entity.Article;
import com.example.tiary.comment.entity.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentRequestDTO {
	private String content;
	private String password;
	private String commentType;

	public Comment toEntity(Article article) {
		return Comment.builder()
			.content(content)
			.password(password)
			.commentType(commentType)
			.article(article)
			.build();
	}
}
