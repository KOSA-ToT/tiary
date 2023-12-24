package com.example.payment.toss.service;

import org.springframework.stereotype.Service;

import com.example.payment.toss.repository.TossPaymentRepository;

@Service
public class TossPaymentService {
	private final TossPaymentRepository tossPaymentRepository;

	public TossPaymentService(TossPaymentRepository tossPaymentRepository) {
		this.tossPaymentRepository = tossPaymentRepository;
	}


}
