package com.example.tiary.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tiary.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findAllByArticleIdOrderByParentIdAscCreatedAtAsc(Long articleId);
}
