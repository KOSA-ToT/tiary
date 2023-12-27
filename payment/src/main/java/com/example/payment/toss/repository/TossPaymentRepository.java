package com.example.payment.toss.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.payment.toss.entity.TossPayment;

public interface TossPaymentRepository extends JpaRepository<TossPayment, Long> {
}
