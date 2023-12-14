package com.example.tiary.global.config.oauth;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class OAuth2FailureHandler implements AuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException {
		System.out.println("OAuth2FailureHandler");
		System.out.println(exception.getMessage());
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		response.sendRedirect("http://localhost:5173/error");
	}
}
