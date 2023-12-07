package com.example.tiary.comment.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tiary.comment.dto.CommentRequestDTO;
import com.example.tiary.comment.dto.CommentResponseDTO;
import com.example.tiary.comment.service.CommentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/comment")
@RestController
public class CommentController {
	private final CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	// 댓글 등록
	@PostMapping("/{article-id}")
	public ResponseEntity create(@PathVariable("article-id") Long articleId,
		@RequestBody CommentRequestDTO commentRequestDTO) {
		return new ResponseEntity<>(commentService.create(commentRequestDTO, articleId), HttpStatus.OK);
	}

	// 전체 댓글 가져오기
	@GetMapping("/{article-id}")
	public ResponseEntity<List<CommentResponseDTO>> readCommentList(@PathVariable("article-id") Long articleId) {
		List<CommentResponseDTO> commentResponseDTOList = commentService.readCommentList(articleId);
		return ResponseEntity.status(HttpStatus.CREATED).body(commentResponseDTOList);
	}

	// 익명 댓글 비밀번호 확인
	@PostMapping("/password-confirm/{comment-id}")
	public ResponseEntity confirmPassword(@PathVariable("comment-id") Long commentId,
		@RequestBody CommentRequestDTO commentRequestDTO) {
		if (commentService.confirmPassword(commentId, commentRequestDTO.getPassword())) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	// 회원 댓글 수정
	@PatchMapping("/{comment-id}")
	public ResponseEntity<CommentResponseDTO> updateComment(
		@PathVariable("comment-id") Long commentId, @RequestBody CommentRequestDTO commentRequestDTO) {
		return ResponseEntity.status(HttpStatus.RESET_CONTENT)
			.body(commentService.update(commentId, commentRequestDTO));
	}

	// 댓글 삭제
	@DeleteMapping("/{comment-id}")
	public ResponseEntity deleteComment(@PathVariable("comment-id") Long commentId) {
		return ResponseEntity.status(HttpStatus.RESET_CONTENT).body(commentService.delete(commentId));
	}
}
