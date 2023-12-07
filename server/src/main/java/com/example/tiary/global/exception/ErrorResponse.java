package com.example.tiary.global.exception;

import java.sql.SQLIntegrityConstraintViolationException;

import lombok.Getter;

@Getter
public class ErrorResponse {
	private int status;
	private String message;
	public ErrorResponse(int status, String message) {
		this.status = status;
		this.message = message;
	}
	public static ErrorResponse of(BusinessLogicException e) {
		return new ErrorResponse(e.getExceptionCode().getStatus(), e.getMessage());
	}

	public static ErrorResponse of(SQLIntegrityConstraintViolationException e){
		if(e.getErrorCode() == 23505){
			return new ErrorResponse(e.getErrorCode(), "요청하신 정보는 중복입니다.");
		}
		return new ErrorResponse(e.getErrorCode(), e.getMessage());
	}
}
