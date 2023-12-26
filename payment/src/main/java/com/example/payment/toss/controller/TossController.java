package com.example.payment.toss.controller;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.payment.toss.config.TossPaymentConfig;
import com.example.payment.toss.dto.PaymentInfoDto;
import com.example.payment.toss.service.TossPaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class TossController {
	private final TossPaymentService tossPaymentService;
	private final TossPaymentConfig tossPaymentConfig;

	@PostMapping("/savepayinfo")
	public void tossPayment(@RequestBody PaymentInfoDto paymentInfoDto) throws JSONException {
		System.out.println(paymentInfoDto);
		tossPaymentService.requestTossPayment(paymentInfoDto, tossPaymentConfig);
	}

}
