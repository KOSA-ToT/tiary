package com.example.tiary.users.constant;

import lombok.Getter;

@Getter
public enum UserStatus {
	ACTIVE("active"),
	INACTIVE("inactive");

	private final String status;

	UserStatus(String status) {
		this.status = status;
	}
}
