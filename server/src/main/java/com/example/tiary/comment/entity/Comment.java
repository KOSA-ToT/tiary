package com.example.tiary.comment.entity;

import static jakarta.persistence.FetchType.*;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import com.example.tiary.article.entity.Article;
import com.example.tiary.global.audit.AuditingFields;
import com.example.tiary.users.entity.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@ToString
@Getter
@Entity
public class Comment extends AuditingFields {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Column(length = 200)
	private String content;

	@Column(length = 20)
	private String password;

	@Column(name = "comment_type")
	private String commentType;

	@ManyToOne(optional = false)
	@JoinColumn(name = "article_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))// 제약키 없이 사용
	private Article article;

	@ColumnDefault("FALSE")
	@Column(nullable = false)
	private Boolean isDeleted;

	@JsonIgnore
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "parent_id")
	@ToString.Exclude
	private Comment parent;

	@OneToMany(mappedBy = "parent", orphanRemoval = true) // 부모댓글이 삭제되면 자식댓글도 삭제 (DB도 삭제)
	@ToString.Exclude
	private List<Comment> children = new ArrayList<>();

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "user_id")
	private Users users;


	public void setParent(Comment parent) {
		this.parent = parent;
	}

	public void updateContent(String content) {
		this.content = content;
	}

	public void updateParent(Comment comment) {
		this.parent = comment;
	}

	public void changeIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
