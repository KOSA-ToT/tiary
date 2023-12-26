package com.example.payment.toss.entity;

import java.time.LocalDateTime;

import com.example.payment.toss.dto.PaymentInfoDto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TossPayment {
	@Id
	private String orderId; // 글 카테고리 포함...
	private String paymentKey;
	private String supporterEmail; // 후원자
	private String articleId; // 후원 받은 글 번호
	private int amount; // 금액
	private LocalDateTime approvedAt; // 승인 일자

	public static TossPayment toEntity(PaymentInfoDto paymentInfoDto, LocalDateTime approvedAt) {
		return TossPayment.builder()
			.orderId(paymentInfoDto.getOrderId())
			.paymentKey(paymentInfoDto.getPaymentKey())
			.supporterEmail(paymentInfoDto.getSupporterEmail())
			.articleId(paymentInfoDto.getArticleId())
			.amount(paymentInfoDto.getAmount())
			.approvedAt(approvedAt)
			.build();
	}
}
