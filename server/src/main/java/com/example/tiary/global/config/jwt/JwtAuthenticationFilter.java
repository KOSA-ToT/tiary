package com.example.tiary.global.config.jwt;

import java.io.IOException;
import java.util.Date;

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
import com.example.tiary.users.dto.LoginDto;
import com.example.tiary.users.entity.Users;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;
	private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER =
		new AntPathRequestMatcher("/auth/login", "POST");

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
		System.out.println("객체 생성");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException, IOException {

		System.out.println("JwtAuthenticationFilter : 진입");

		ObjectMapper om = new ObjectMapper();
		LoginDto loginDto = om.readValue(request.getInputStream(), LoginDto.class);

		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(loginDto.getEmail(), "");

		System.out.println("JwtAuthenticationFilter : 토큰생성완료");

		return customAuthenticationProvider.authenticate(authenticationToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request,
		HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
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

}
