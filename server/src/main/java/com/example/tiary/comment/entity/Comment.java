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
@Getter
@Entity
public class Comment extends AuditingFields {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Column(length = 200) // 명세에 충실한 느낌을 줌 varchar 255
	private String content;

	@Column(length = 20) // 널이 되는애가 있어 -> content -> 5000 -> 서버 스펙이 후져요 램 2g -> 그런걸 해봤냐
	// 메모리 최적화
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
	@ManyToOne(fetch = LAZY) // 이거 왜 씀?
	@JoinColumn(name = "parent_id")
	private Comment parent;

	@OneToMany(mappedBy = "parent", orphanRemoval = true) // 부모댓글이 삭제되면 자식댓글도 삭제 (DB도 삭제)
	private List<Comment> children = new ArrayList<>();

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "user_id")
	private Users users;

	// 이런게 관리 안된 코드라는 느낌을 줌ㅇ
	// public void setParent(Comment parent) {
	// 	this.parent = parent;
	// }

	public void updateContent(String content) {
		this.content = content;
	}

	public void updateParent(Comment comment) {
		this.parent = comment;
	}

	// public void changeIsDeleted(Boolean isDeleted) {
	// 	this.isDeleted = isDeleted;
	// }
}
