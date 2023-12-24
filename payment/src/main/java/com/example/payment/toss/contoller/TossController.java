package com.example.payment.toss.contoller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.payment.toss.config.TossPaymentConfig;
import com.example.payment.toss.dto.PaymentInfo;
import com.example.payment.toss.dto.RequestTossPaymentDto;
import com.example.payment.toss.service.TossPaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class TossController {
	private final TossPaymentService tossPaymentService;
	private final TossPaymentConfig tossPaymentConfig;
	private final RestTemplate restTemplate;

	@GetMapping("/success")
	public String tossPayment(@RequestParam("orderId") String orderId,
										@RequestParam("paymentKey") String paymentKey,
											@RequestParam("amount") String amount) throws JSONException {
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
						""".formatted(amount, orderId, paymentKey);

		HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

		ResponseEntity<String> responseEntity = restTemplate.postForEntity(
			tossPaymentConfig.getPaymentUrl(),
			requestEntity,
			String.class
		);

		System.out.println(responseEntity.getBody());

		// 응답 받기
		return responseEntity.getBody();
	}
}
