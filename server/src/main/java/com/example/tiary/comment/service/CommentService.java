package com.example.tiary.comment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	public Comment create(CommentRequestDTO commentRequestDTO, Long articleId) {
		Article article = articleRepository.findById(articleId)
			.orElseThrow(() -> new EntityNotFoundException("게시물이 존재하지 않습니다."));

		return commentRepository.save(commentRequestDTO.toEntity(article));
	}

	// 전체 댓글
	@Transactional(readOnly = true) // 직관적으로 이거는 조회하는 메서드임을 알려줌
	public List<CommentResponseDTO> readCommentList(Long articleId) {
		List<Comment> commentList = commentRepository.findAllByArticleId(articleId);
		List<CommentResponseDTO> commentResponseDTOList = new ArrayList<>();
		CommentResponseDTO commentResponseDTO = new CommentResponseDTO();
		for (Comment comment : commentList) {
			commentResponseDTOList.add(new CommentResponseDTO().from(comment));
		}
		return commentResponseDTOList;
	}

	// 수정
	@Transactional
	public CommentResponseDTO update(Long commentId, CommentRequestDTO commentRequestDTO) {
		Comment comment = commentRepository.findById(commentId).get();
		// if(!commentRequestDTO.getContent().isBlank()){
		// 	comment.updateContent(commentRequestDTO.getContent());
		// }
		Optional.ofNullable(commentRequestDTO.getContent()).ifPresent(comment::updateContent);

		CommentResponseDTO commentResponseDTO = new CommentResponseDTO();
		return commentResponseDTO.from(commentRepository.save(comment));
	}

	// 삭제
	public String delete(Long commentId) {
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new EntityNotFoundException("댓글이 이미 삭제되었습니다."));
		commentRepository.deleteById(commentId);
		return "삭제 완료";
	}
}
