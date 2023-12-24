package com.example.payment.toss.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class PaymentInfo {
	private int amount;
	private String orderId;
	private String paymentKey;
}
