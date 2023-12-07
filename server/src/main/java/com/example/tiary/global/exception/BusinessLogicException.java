package com.example.tiary.global.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BusinessLogicException extends RuntimeException{
	private final ExceptionCode exceptionCode;

	@Builder
	public BusinessLogicException(String message,ExceptionCode exceptionCode){
		super(message);
		this.exceptionCode = exceptionCode;
	}

	@Builder
	public BusinessLogicException(ExceptionCode exceptionCode){
		super(exceptionCode.getMessage());
		this.exceptionCode = exceptionCode;
	}

}
