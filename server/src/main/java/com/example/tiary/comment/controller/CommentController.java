package com.example.tiary.comment.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tiary.comment.dto.request.CommentRequestDTO;
import com.example.tiary.comment.dto.response.CommentResponseDTO;
import com.example.tiary.comment.service.CommentService;
import com.example.tiary.users.dto.UserDto;

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
		@RequestBody CommentRequestDTO commentRequestDTO,
		@AuthenticationPrincipal UserDto users) {
		return new ResponseEntity<>(commentService.create(commentRequestDTO, articleId, users.getUsers().getId()), HttpStatus.OK);
	}

	//  비회원 댓글 등록
	@PostMapping("/guest/{article-id}")
	public ResponseEntity guestCreate(@PathVariable("article-id") Long articleId,
		@RequestBody CommentRequestDTO commentRequestDTO) {
		return new ResponseEntity<>(commentService.guestCreate(commentRequestDTO, articleId), HttpStatus.OK);
	}

	// 전체 댓글 읽기
	@GetMapping("/{article-id}")
	public ResponseEntity<List<CommentResponseDTO>> readCommentList(@PathVariable("article-id") Long articleId) {
		List<CommentResponseDTO> commentResponseDTOList = commentService.readCommentList(articleId);
		return ResponseEntity.status(HttpStatus.CREATED).body(commentResponseDTOList);
	}

	// 익명 댓글 비밀번호 확인
	@PostMapping("/guest/password-confirm/{comment-id}")
	public ResponseEntity confirmPassword(@PathVariable("comment-id") Long commentId,
		@RequestBody CommentRequestDTO commentRequestDTO) {
		System.out.println(commentRequestDTO);
		if (commentService.confirmPassword(commentId, commentRequestDTO.getPassword())) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	// 댓글 수정
	@PatchMapping("/{article-id}/{comment-id}")
	public ResponseEntity<CommentResponseDTO> updateComment(
		@PathVariable("comment-id") Long commentId, @RequestBody CommentRequestDTO commentRequestDTO, @PathVariable("article-id") Long articleId,  @AuthenticationPrincipal UserDto users) {
		return ResponseEntity.status(HttpStatus.RESET_CONTENT)
			.body(commentService.update(commentId, commentRequestDTO, articleId, users.getUsers().getId()));
	}

	// 비회원 댓글 수정
	@PatchMapping("/guest/{article-id}/{comment-id}")
	public ResponseEntity<CommentResponseDTO> guestUpdateComment(
		@PathVariable("comment-id") Long commentId,@PathVariable("article-id") Long articleId,  @RequestBody CommentRequestDTO commentRequestDTO) {
		return ResponseEntity.status(HttpStatus.RESET_CONTENT)
			.body(commentService.guestUpdate(commentId, articleId, commentRequestDTO));
	}

	// 회원 댓글 삭제
	@DeleteMapping("/{article-id}/{comment-id}")
	public ResponseEntity deleteComment(@PathVariable("comment-id") Long commentId,  @PathVariable("article-id") Long articleId,  @AuthenticationPrincipal UserDto users) {
		return ResponseEntity.status(HttpStatus.RESET_CONTENT).body(commentService.delete(commentId, articleId, users.getUsers().getId()));
	}

	// 비회원 댓글 삭제
	@DeleteMapping("/guest/{article-id}/{comment-id}")
	public ResponseEntity guestDeleteComment(@PathVariable("comment-id") Long commentId) {
		return ResponseEntity.status(HttpStatus.RESET_CONTENT).body(commentService.guestDelete(commentId));
	}
}
