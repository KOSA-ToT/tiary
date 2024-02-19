package com.example.tiary.users.constant;

import lombok.Getter;

@Getter
public enum UserPicture {
	// Enum 상수에 파일명 지정
	DEFAULT_PICTURE("f72c40b0-9c62-4cbc-bc2c-ea8eeb2c4ff1-loading_1.gif");

	private final String fileName;

	UserPicture(String fileName) {
		this.fileName = fileName;
	}

}
