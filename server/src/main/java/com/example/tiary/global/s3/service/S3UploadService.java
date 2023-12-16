package com.example.tiary.global.s3.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3UploadService {
	private static final int CAPACITY_LIMIT_BYTE = 1024 * 1024 * 10;
	private final AmazonS3 amazonS3;
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;


/*
 		public List<String> uploads(List<MultipartFile> multipartFiles) throws IOException {
		List<String> imgUrlList = new ArrayList<>();
		if (multipartFiles.isEmpty()) {
			return imgUrlList;
		}
		for (MultipartFile multipartFile : multipartFiles) {
			if (!multipartFile.isEmpty()) {
				imgUrlList.add(upload(multipartFile));
			}
		}
		return imgUrlList;
	}*/

	public String upload(MultipartFile multipartFile, String storeName) throws IOException {

		if (multipartFile.isEmpty()) {
			return null;
		}
		// s3에 저장되는 파일이름 중복안되게 하기
		String ext = extractExt(storeName);
		String contentType = "";

		//파일크기 용량제한 넘으면 예외 던지기
		if (multipartFile.getSize() > CAPACITY_LIMIT_BYTE) {
			throw new RuntimeException("이미지가 10M 제한을 넘어갑니다.");
		}
		contentType = switch (ext) {
			case "jpeg" -> "image/jpeg";
			case "png" -> "image/png";
			case "txt" -> "text/plain";
			case "csv" -> "text/csv";
			default -> contentType;
		};

		try {
			ObjectMetadata objMeta = new ObjectMetadata();
			objMeta.setContentType(contentType);
			objMeta.setContentLength(multipartFile.getInputStream().available()); //파일의 사이즈 S3에 알려주기
			amazonS3.putObject(bucket, storeName, multipartFile.getInputStream(), objMeta); //S3 API 메소드 이용해서 S3에 파일 업로드
			//            return amazonS3.getUrl(bucket, s3FileName).toString(); //getUrl로 S3에 업로드된 사진 URL 가져오기
		} catch (SdkClientException e) {
			e.printStackTrace();
		}
		return URLDecoder.decode(amazonS3.getUrl(bucket, storeName).toString(), StandardCharsets.UTF_8);
	}

	//키 값으로 url 가져오기
	public String getS3URL(String storeName) throws UnsupportedEncodingException {
		return URLDecoder.decode(amazonS3.getUrl(bucket, storeName).toString(), StandardCharsets.UTF_8);
	}

	/*  파일 이름이 이미 업로드된 파일들과 겹치지 않게 UUID를 사용한다.   */
	private String createStoreFileName(String originalFilename) {
		String ext = extractExt(originalFilename);
		String uuid = UUID.randomUUID().toString();
		return uuid + "." + ext;
	}

	/*  사용자가 업로드한 파일에서 확장자를 추출한다.   */
	private String extractExt(String originalFilename) {
		int pos = originalFilename.lastIndexOf(".");
		return originalFilename.substring(pos + 1);
	}

	public void deleteImage(String originalFilename) {
		DeleteObjectRequest request = new DeleteObjectRequest(bucket, originalFilename);
		amazonS3.deleteObject(request);
	}
}
