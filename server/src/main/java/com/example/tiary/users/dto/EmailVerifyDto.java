package com.example.tiary.users.dto;

import org.springframework.web.bind.annotation.RequestParam;

import lombok.Getter;

@Getter
public class EmailVerifyDto {
	private String encodedKey;
	private String task;
}
