package com.example.tiary.global.s3.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.tiary.global.s3.service.ArticleImageService;

@RestController
@RequestMapping("/images")
public class S3Controller {
	private final ArticleImageService articleImageService;

	public S3Controller(ArticleImageService articleImageService) {
		this.articleImageService = articleImageService;
	}

	@PostMapping
	public ResponseEntity postImages(@RequestPart("images") List<MultipartFile> multipartFiles) throws IOException {
		Map<String, String> images = articleImageService.saveArticleImages(multipartFiles);
		return new ResponseEntity(images, HttpStatus.CREATED);
	}
}
