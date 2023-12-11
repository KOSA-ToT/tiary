package com.example.tiary.global.config.jwt;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.tiary.global.config.auth.CustomAuthenticationProvider;
import com.example.tiary.global.exception.ExceptionCode;
import com.example.tiary.users.dto.LoginDto;
import com.example.tiary.users.entity.Users;
import com.example.tiary.users.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER =
		new AntPathRequestMatcher("/auth/login", "POST");
	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;
	private final UserService userService;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService) {
		super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
		this.userService = userService;
		System.out.println("객체 생성");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException, IOException {
		ObjectMapper om = new ObjectMapper();
		try {
			LoginDto loginDto = om.readValue(request.getInputStream(), LoginDto.class);
			UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(loginDto.getEmail(), "");
			return customAuthenticationProvider.authenticate(authenticationToken);
		} catch (UnrecognizedPropertyException e) {
			response.setStatus(ExceptionCode.BAD_REQUEST.getStatus());
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			String errorMsg = om.writeValueAsString(Map.of("error", ExceptionCode.BAD_REQUEST.getMessage()));

			response.getWriter().write(errorMsg);
			return null;
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request,
		HttpServletResponse response, FilterChain chain,
		Authentication authResult) {
		System.out.println("successfulAuthentication ");
		Users principalDetails = (Users)authResult.getPrincipal();
		String jwtToken = JWT.create()
			.withSubject(principalDetails.getEmail())
			.withExpiresAt(new Date(System.currentTimeMillis()
				+ JwtProperties.EXPIRATION_TIME))
			.withClaim("id", principalDetails.getId())
			.withClaim("email", principalDetails.getEmail())
			.sign(Algorithm.HMAC512(JwtProperties.SECRET));
		response.addHeader(JwtProperties.HEADER_STRING,
			JwtProperties.TOKEN_PREFIX + jwtToken);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException failed) throws IOException {
		logger.info("인증 실패: unsuccessfulAuthentication");
		response.setStatus(ExceptionCode.UNAUTHORIZED.getStatus());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		ObjectMapper om = new ObjectMapper();
		String errorMsg = om.writeValueAsString(Map.of("error", failed.getMessage()));

		response.getWriter().write(errorMsg);
	}
}
