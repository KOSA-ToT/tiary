package com.example.payment.toss.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TossPayment {
	@Id
	private String orderId; // 글 카테고리 포함...
	private String paymentKey;
	private String supporterId; // 후원자
	private String receiverId; // 후원 받은 사람
	private String articleId; // 후원 받은 글 번호
	private int amount; // 금액
	private LocalDateTime approvedAt; // 승인 일자
}
