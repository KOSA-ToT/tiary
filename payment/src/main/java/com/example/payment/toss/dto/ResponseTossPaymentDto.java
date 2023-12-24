package com.example.payment.toss.dto;

import lombok.Getter;

@Getter
public class ResponseTossPaymentDto {
	// 0 = 성공, -1 = 실패
	private int code;
	private String checkoutPage;
	private String payToken;
	private String msg;
	private String errorCode;
}
