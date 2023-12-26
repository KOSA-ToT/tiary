package com.example.payment.toss.dto;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class PaymentInfoDto {
	private String orderId;
	private String paymentKey;
	private int amount;
	private String articleId;
	private String supporterEmail;
}
