package com.example.tiary.comment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	CommentService commentService;

	@PostMapping("/{article-id}")
	public ResponseEntity create(@PathVariable("article-id") Long articleId,
		@RequestBody CommentRequestDTO commentRequestDTO) {

		return new ResponseEntity<>(commentService.create(commentRequestDTO, articleId), HttpStatus.OK);
	}

	@GetMapping("/{article-id}")
	public ResponseEntity<List<CommentResponseDTO>> readCommentList(@PathVariable("article-id") Long articleId) {
		List<CommentResponseDTO> commentResponseDTOList = commentService.readCommentList(articleId);

		return ResponseEntity.status(HttpStatus.CREATED).body(commentResponseDTOList);
	}

	@PatchMapping("/{comment-id}")
	public ResponseEntity<CommentResponseDTO> updateComment(
		@PathVariable("comment-id") Long commentId, @RequestBody CommentRequestDTO commentRequestDTO) {

		return ResponseEntity.status(HttpStatus.RESET_CONTENT)
			.body(commentService.update(commentId, commentRequestDTO));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deleteComment(@PathVariable ("id") Long commentId) {

		return ResponseEntity.status(HttpStatus.RESET_CONTENT).body(commentService.delete(commentId));
	}

}
