package com.example.tiary.article.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.tiary.users.entity.Users;

import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@Entity
@Table(
	uniqueConstraints = {
		@UniqueConstraint(
			name = "articleLike",
			columnNames = {"article_id", "users_id"}
		)
	}
)
public class ArticleLikes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "article_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Article article;

	@ManyToOne
	@JoinColumn(name = "users_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Users users;

	public static ArticleLikes of(Article article, Users users) {
		return new ArticleLikes(null, article, users);
	}
}
