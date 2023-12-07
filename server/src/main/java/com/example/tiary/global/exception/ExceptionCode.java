package com.example.tiary.global.exception;

import lombok.Getter;

public enum ExceptionCode {

	UNAUTHORIZED(401, "미인증 사용자 입니다."),
	ARTICLE_NOT_FOUND(404,"게시물이 없습니다"),
	ARTICLE_ALREADY_DELETE(404,"게시물이 이미 삭제 되었습니다"),
	USER_NOT_FOUND(404,"사용자가 없습니다."),
	USER_EXIST(500,"사용자가 이미 있습니다."),
	COMMENTS_NOT_FOUND(404,"댓글이 없습니다.");

	@Getter
	private  int status;

	@Getter
	private  String message;

	ExceptionCode(int status, String message){
		this.status = status;
		this.message = message;
	}
}
