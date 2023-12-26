package com.example.payment.toss.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class PaymentInfo {
	private String orderId;
	private String paymentKey;
	private int amount;
}
