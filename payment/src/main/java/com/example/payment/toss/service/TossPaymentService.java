package com.example.payment.toss.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Collections;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.example.payment.toss.config.TossPaymentConfig;
import com.example.payment.toss.dto.PaymentInfoDto;
import com.example.payment.toss.dto.TossPaymentDto;
import com.example.payment.toss.entity.TossPayment;
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
	public void requestTossPayment(PaymentInfoDto paymentInfoDto, TossPaymentConfig tossPaymentConfig) throws JSONException {
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
						""".formatted(paymentInfoDto.getAmount(), paymentInfoDto.getOrderId(), paymentInfoDto.getPaymentKey());

		HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

		ResponseEntity<String> responseEntity = restTemplate.postForEntity(
			tossPaymentConfig.getPaymentUrl() + "/confirm",
			requestEntity,
			String.class
		);
		System.out.println(responseEntity.getBody());

		JSONObject jsonObject = new JSONObject(responseEntity.getBody());

		LocalDateTime approvedAt = parseZonedDateTime(jsonObject);

		tossPaymentRepository.save(TossPayment.toEntity(paymentInfoDto, approvedAt));
	}

	private LocalDateTime parseZonedDateTime(JSONObject jsonObject) throws JSONException {
		// ZonedDateTime으로 파싱
		ZonedDateTime zonedDateTime = ZonedDateTime.parse(jsonObject.getString("approvedAt"));
		// ZonedDateTime에서 LocalDateTime으로 변환
		return zonedDateTime.toLocalDateTime();
	}


}
