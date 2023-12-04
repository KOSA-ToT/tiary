package com.example.tiary.comment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	public ResponseEntity save(@PathVariable("article-id") Long articleId,
		@RequestBody CommentRequestDTO commentRequestDTO) {

		return new ResponseEntity<>(commentService.save(commentRequestDTO,articleId),HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<CommentResponseDTO>> readAll() {
		List<CommentResponseDTO> commentResponseDTOList = commentService.readAll();
		return ResponseEntity.status(HttpStatus.CREATED).body(commentResponseDTOList);
	}

}
