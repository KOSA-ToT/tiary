package com.example.tiary.global.config.auth;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.example.tiary.global.exception.ExceptionCode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException {

		response.setStatus(ExceptionCode.UNAUTHORIZED.getStatus());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		ObjectMapper om = new ObjectMapper();
		String errorMsg = om.writeValueAsString(Map.of("error", ExceptionCode.UNAUTHORIZED.getMessage()));
		response.getWriter().write(errorMsg);
	}
}
