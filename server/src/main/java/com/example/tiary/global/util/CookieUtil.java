package com.example.tiary.global.util;

import java.time.Duration;
import java.util.Optional;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {

	public static void createCookie(HttpServletResponse response, String name, String value, Duration expireTime) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge((int)expireTime.getSeconds());
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
	}

	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				cookie.setValue("");
				cookie.setPath("/");
				cookie.setMaxAge(0); // 쿠키 유효 시간을 0으로 설정 (=쿠키 삭제)
				response.addCookie(cookie);
				return;
			}
		}
	}

	public static String getValueFromCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		return Optional.ofNullable(cookies)
				.map(cookie -> {
					for (Cookie c : cookie) {
						if (c.getName().equals(name)) {
							return c.getValue();
						}
					}
					return "";
				}).orElse("");
	}
}
