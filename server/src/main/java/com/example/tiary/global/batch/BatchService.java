package com.example.tiary.global.batch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BatchService {

	@Value("${batch.server.url}")
	private String batchServerUrl;

	private RestTemplate restTemplate;

	public BatchService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	// @Async
	// public void updateRecommendationsAsync(Long articleId){
	// 	String apiUrl = batchServerUrl + "/batch/updateRecommendations/" + articleId;
	//
	// 	restTemplate.postForEntity(apiUrl, null, Void.class);
	// }

	public void updateRecommendationsAsync(Long postId) {
		//http://localhost:8089
		String requestUrl  =  batchServerUrl + "/batch/updateRecommendations/" + postId;
		System.out.println("서버 호스트 입니다: " + batchServerUrl);

		// 게시물이 생성된 후 배치 서버 API를 호출
		ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, null, String.class);

		// HTTP 응답 확인 등 추가 로직이 필요할 경우 처리 가능
	}
}
