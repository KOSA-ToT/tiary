package com.example.payment.toss.dto;

import lombok.Getter;

@Getter
public class PaymentInfo {
	private int amount;
	private String orderId;
	private String orderName;
	private String customerName;
	private String successUrl;
	private String failUrl;
}
