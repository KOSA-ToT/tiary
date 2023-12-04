package com.example.tiary.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tiary.article.entity.Article;
import com.example.tiary.article.repository.ArticleRepository;
import com.example.tiary.comment.dto.CommentRequestDTO;
import com.example.tiary.comment.dto.CommentResponseDTO;
import com.example.tiary.comment.entity.Comment;
import com.example.tiary.comment.repository.CommentRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CommentService {
	private final CommentRepository commentRepository;
	private final ArticleRepository articleRepository;

	public CommentService(CommentRepository commentRepository, ArticleRepository articleRepository) {
		this.commentRepository = commentRepository;
		this.articleRepository = articleRepository;
	}

	//저장
	@Transactional
	public Comment save(CommentRequestDTO commentRequestDTO, Long articleId) {
		Article article = articleRepository.findById(articleId)
			.orElseThrow(() -> new EntityNotFoundException("게시물이 존재하지 않습니다."));

		return commentRepository.save(commentRequestDTO.toEntity(article));
	}

	@Transactional(readOnly = true) // 직관적으로 이거는 조회하는 메서드임을 알려줌
	public List<CommentResponseDTO> readAll() {
		List<Comment> commentList = commentRepository.findAll();
		List<CommentResponseDTO> commentResponseDTOList = null;
		CommentResponseDTO commentResponseDTO = null;
		for (Comment comment : commentList) {
			commentResponseDTOList.add(commentResponseDTO.from(comment));
		}
		return commentResponseDTOList;
	}
}
