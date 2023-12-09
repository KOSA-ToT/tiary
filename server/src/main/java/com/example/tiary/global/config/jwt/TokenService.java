package com.example.tiary.global.config.jwt;

import java.time.Duration;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.tiary.global.exception.BusinessLogicException;
import com.example.tiary.global.exception.ExceptionCode;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TokenService {
	private final String secretKey;

	public TokenService(@Value("${jwt.secret}") String secretKey) {
		this.secretKey = secretKey;
	}

	public String createAccessToken(Long id, String email, Duration expireTime) {
		return JWT.create()
			.withSubject(email)
			.withExpiresAt(new Date(System.currentTimeMillis()
				+ expireTime.toMillis()))
			.withClaim("id", id)
			.withClaim("email", email)
			.sign(Algorithm.HMAC512(secretKey));
	}

	public String validateAndExtractSubjectFromToken(String token) {
		// 액세스 토큰이 빈 값이거나(OAuth2를 위함) 만료된 토큰일 경우 null 반환
		try {
			if (token.isEmpty()) {
				return null;
			}
			return JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token).getSubject();
		} catch (TokenExpiredException e) {
			return null;
		} catch (Exception e) {
			throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
		}
	}

	public String getTokenFromHeader(HttpServletRequest request) {
		String header = request.getHeader(JwtProperties.getHEADER_STRING());
		if (header == null) {
			return "";
		} else if (!header.startsWith(JwtProperties.getTOKEN_PREFIX())) {
			throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
		} else {
			return header.replace(JwtProperties.getTOKEN_PREFIX(), "");
		}
	}
}
