package com.example.payment.toss.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TossPayment {
	@Id
	private String orderNo;

	// fk 설정...?
	private String supporterId;
	private String receiverId;
	private String couponName;
	private int amount;


}
