package com.example.payment.toss.contoller;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.payment.toss.config.TossPaymentConfig;
import com.example.payment.toss.dto.PaymentInfo;
import com.example.payment.toss.service.TossPaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class TossController {
	private final TossPaymentService tossPaymentService;
	private final TossPaymentConfig tossPaymentConfig;

	@GetMapping("/success")
	public void tossPayment(@RequestParam("orderId") String orderId,
										@RequestParam("paymentKey") String paymentKey,
											@RequestParam("amount") String amount) throws JSONException {
		tossPaymentService.requestTossPayment(new PaymentInfo(orderId, paymentKey, Integer.parseInt(amount)), tossPaymentConfig);



	}
}
