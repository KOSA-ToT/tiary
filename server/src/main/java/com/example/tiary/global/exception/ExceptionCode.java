package com.example.tiary.global.exception;

import lombok.Getter;

public enum ExceptionCode {

	BAD_REQUEST(400, "잘못된 요청입니다."),
	UNAUTHORIZED(401, "미인증 사용자 입니다."),
	ARTICLE_NOT_FOUND(404, "게시물이 없습니다"),
	ARTICLE_ALREADY_DELETE(404, "게시물이 이미 삭제 되었습니다"),
	USER_NOT_FOUND(404, "사용자가 없습니다."),
	USER_EXIST(500, "사용자가 이미 있습니다."),
	USER_EXIST_INACTIVE(500, "탈퇴한 사용자입니다."),
	COMMENTS_NOT_FOUND(404, "댓글이 없습니다."),
	COMMENTS_ALREADY_DELETE(404, "댓글이 이미 삭제되었습니다."),
	PARENT_COMMENTS_NOT_FOUND(404, "없는 부모 댓글입니다.");

	@Getter
	private int status;

	@Getter
	private String message;

	ExceptionCode(int status, String message) {
		this.status = status;
		this.message = message;
	}
	}
