package com.example.tiary.comment.dto.response;

import java.time.LocalDateTime;
import java.util.List;

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
public class CommentResponseDTO {
	private Long id;
	private String content;
	private String password;
	private String commentType;
	private Long articleId;
	private List<CommentResponseDTO> children;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	private String createdBy;
	private String modifiedBy;

	public static CommentResponseDTO from(Comment comment) {
		return
			CommentResponseDTO.builder()
				.id(comment.getId())
				.content(comment.getContent())
				.password(comment.getPassword())
				.commentType(comment.getCommentType())
				.articleId(comment.getArticle().getId())
				.createdAt(comment.getCreatedAt())
				.modifiedAt(comment.getModifiedAt())
				.createdBy(comment.getCreatedBy())
				.modifiedBy(comment.getModifiedBy())
				.build();
	}

}
