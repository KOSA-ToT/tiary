package com.example.payment.toss.contoller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.payment.toss.config.TossPaymentCofig;
import com.example.payment.toss.dto.PaymentInfo;
import com.example.payment.toss.dto.RequestTossPaymentDto;
import com.example.payment.toss.service.TossPaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class TossController {
	private final TossPaymentService tossPaymentService;
	private final TossPaymentCofig tossPaymentCofig;
	private final RestTemplate restTemplate;

	@PostMapping("/toss")
	public ResponseEntity tossPayment(@RequestBody PaymentInfo paymentInfo) {

		return restTemplate.postForEntity(tossPaymentCofig.getPaymentUrl(), requestTossPaymentDto, String.class);
	}
}
