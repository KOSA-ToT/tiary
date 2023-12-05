package com.example.tiary.comment.entity;

import com.example.tiary.article.entity.Article;
import com.example.tiary.global.audit.AuditingFields;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@Entity
public class Comment extends AuditingFields {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Column(length = 200)
	private String content;

	@ManyToOne
	private Article article;

	public void updateContent(String content){
		this.content = content;
	}

	//    @ManyToOne
	//    @JoinColumn(name = "user_id")
	//    private Users users;

}
