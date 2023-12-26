package com.example.payment.toss.controller;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
	public ResponseEntity tossPayment(@RequestBody PaymentInfoDto paymentInfoDto) throws JSONException {
		try {
			System.out.println(paymentInfoDto);
			tossPaymentService.requestTossPayment(paymentInfoDto, tossPaymentConfig);
			return ResponseEntity.ok().build(); // 200 OK
		} catch (Exception e) {
			// 예외 발생 시 500 Internal Server Error
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
