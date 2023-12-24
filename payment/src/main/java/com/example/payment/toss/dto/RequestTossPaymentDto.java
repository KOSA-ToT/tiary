package com.example.payment.toss.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class RequestTossPaymentDto {
	// toss pay 필수 params
	private String orderNo; // 주문 번호
	private int amount; // 결제 금액
	private int amountTaxFree; // 비과세 금액
	private String productDesc; // 상품명
	private String apiKey; // 상점 api key
	private boolean autoExecute; // 자동 승인 설정(구매자 인증 완료되면 토스가 바로 승인 하는지)
	private String resultCallback; // 결제 결과 callback api(토스가 출금 성공 후 가맹점 서버로 callback을 HttpMethod POST 로 요청)
	private String retUrl; // 인증 완료 후 연결할 URL
	private String retCancelUrl; // 결제 중단 시 사용자를 이동시킬 가맹점 페이지

	public static RequestTossPaymentDto requestTossPaymentDto(PaymentInfo paymentInfo) {
		return RequestTossPaymentDto.builder()
			.orderNo()
			.amount(paymentInfo.getAmount())
			.amountTaxFree(0)
			.productDesc(paymentInfo.getOrderName())
			.apiKey()
			.autoExecute(true)
			.resultCallback()
			.retUrl(paymentInfo.getSuccessUrl())
			.retCancelUrl(paymentInfo.getFailUrl())
			.build();
	}
}
