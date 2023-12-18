package com.example.tiary.global.config.jwt;

import java.time.Duration;

import lombok.Getter;

public class JwtProperties {
	// Token 관련 설정
	@Getter
	private static final String TOKEN_PREFIX = "Bearer ";
	@Getter
	private static final String HEADER_STRING = "Authorization";

	// AccessToken 관련 설정
	@Getter
	private static final Duration ACCESS_TOKEN_EXPIRE_DURATION = Duration.ofMinutes(1);

	// RefreshToken 관련 설정
	@Getter
	private static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";
	@Getter
	private static final Duration REFRESH_TOKEN_EXPIRE_DURATION = Duration.ofDays(3);
}
