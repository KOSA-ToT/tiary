package com.example.tiary.global.exception;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessLogicException.class)
	protected ResponseEntity handleBusinessLogicException(BusinessLogicException e){
		log.debug("로그 위치 확인 : {}",e.getExceptionCode());
		ErrorResponse response = ErrorResponse.of(e);
		return new ResponseEntity<>(response, HttpStatusCode.valueOf(e.getExceptionCode().getStatus()));
	}

	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	protected ResponseEntity handleSQLException(SQLIntegrityConstraintViolationException e){
		log.debug("로그 위치 확인 : {}",e.getErrorCode());
		ErrorResponse response = ErrorResponse.of(e);
		return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
