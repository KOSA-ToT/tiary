package com.example.tiary.comment.dto.request;

import org.apache.catalina.User;

import com.example.tiary.article.entity.Article;
import com.example.tiary.comment.entity.Comment;
import com.example.tiary.users.entity.Users;

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
	private Long parentId;

	public Comment toEntityUser(Article article, Users users) {
		return Comment.builder()
			.content(content)
			.password(password)
			.commentType(commentType)
			.article(article)
			.users(users)
			.build();
	}
	public Comment toEntityGuest(Article article) {
		return Comment.builder()
			.content(content)
			.password(password)
			.commentType(commentType)
			.article(article)
			.users(null)
			.build();
	}
}
