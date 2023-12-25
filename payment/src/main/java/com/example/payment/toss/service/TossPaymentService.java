package com.example.payment.toss.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Collections;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.example.payment.toss.config.TossPaymentConfig;
import com.example.payment.toss.dto.PaymentInfo;
import com.example.payment.toss.repository.TossPaymentRepository;

@Service
public class TossPaymentService {
	private final TossPaymentRepository tossPaymentRepository;
	private final RestTemplate restTemplate;

	public TossPaymentService(TossPaymentRepository tossPaymentRepository, RestTemplate restTemplate) {
		this.tossPaymentRepository = tossPaymentRepository;
		this.restTemplate = restTemplate;
	}

	@Transactional
	public void requestTossPayment(PaymentInfo paymentInfo, TossPaymentConfig tossPaymentConfig) throws JSONException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		String apiKey = tossPaymentConfig.getSecretKey() + ":";
		String encodedAuth = new String(Base64.getEncoder().encode(apiKey.getBytes(StandardCharsets.UTF_8)));
		headers.setBasicAuth(encodedAuth);

		String jsonBody = """
						{
							"amount": "%s",
							"orderId": "%s",
							"paymentKey": "%s"
						}
						""".formatted(paymentInfo.getAmount(), paymentInfo.getOrderId(), paymentInfo.getPaymentKey());

		HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

		ResponseEntity<String> responseEntity = restTemplate.postForEntity(
			tossPaymentConfig.getPaymentUrl() + "/confirm",
			requestEntity,
			String.class
		);
		System.out.println(responseEntity.getBody());
		// 필요 데이터 저장
		savePaymentRecord(responseEntity.getBody());
	}

	public void savePaymentRecord(String responseBody) throws JSONException {
		JSONObject jsonObject = new JSONObject(responseBody);
		String orderId = jsonObject.getString("orderId");
		String paymentKey = jsonObject.getString("paymentKey");
		String supporterId = jsonObject.getString("supporterId");
		String receiverId = jsonObject.getString("receiverId");
		String articleId = jsonObject.getString("articleId");
		int amount = Integer.parseInt(jsonObject.getString("amount"));
		LocalDateTime approvedAt = LocalDateTime.parse(jsonObject.getString("approvedAt"));

		System.out.println("오더 아이디: " + orderId);
		System.out.println("페이먼트 키: " + paymentKey);
		System.out.println("보낸사람: " + supporterId);
		System.out.println("받는사람: " + receiverId);
		System.out.println("후원 받은 글 번호: " + articleId);
		System.out.println("얼마: " + amount);
		System.out.println("날짜: " + approvedAt);
		
	}
}
