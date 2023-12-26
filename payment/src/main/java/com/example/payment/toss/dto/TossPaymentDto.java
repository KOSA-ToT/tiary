package com.example.payment.toss.dto;

import java.time.LocalDateTime;

import com.example.payment.toss.entity.TossPayment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TossPaymentDto {
	private String orderId; // 글 카테고리 포함...
	private String paymentKey;
	private String supporterEmail; // 후원자
	private String articleId; // 후원 받은 글 번호
	private int amount; // 금액
	private LocalDateTime approvedAt; // 승인 일자
}
