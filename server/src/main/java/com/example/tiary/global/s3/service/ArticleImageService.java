package com.example.tiary.global.s3.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ArticleImageService {
	private final S3UploadService s3UploadService;

	public ArticleImageService(S3UploadService s3UploadService) {
		this.s3UploadService = s3UploadService;
	}

	@Transactional
	public Map<String, String> saveArticleImages(List<MultipartFile> multipartFileList) throws IOException {
		Map<String, String> imagesSet = new HashMap<>();
		for (MultipartFile image : multipartFileList) {
			String storeName = UUID.randomUUID() + "-" + image.getOriginalFilename();
			s3UploadService.upload(image, storeName);
			String objectUrl =  s3UploadService.getS3URL(storeName);
			imagesSet.put(storeName, objectUrl);
		}
		return imagesSet;
	}
}
