package com.example.payment.toss.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class TossPaymentConfig {
	@Value("${toss.payments.test-secret-key}")
	private String secretKey;

	@Value("${toss.payments.success-url}")
	private String successUrl;

	@Value("${toss.payments.fail-url}")
	private String failUrl;

	@Value("${toss.payments.payment-url}")
	private String paymentUrl;
}

