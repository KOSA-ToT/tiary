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

	public String createToken(Long id, String email, Duration expireTime) {
		return JWT.create()
			.withSubject(email)
			.withExpiresAt(new Date(System.currentTimeMillis()
				+ expireTime.toMillis()))
			.withClaim("id", id)
			.withClaim("email", email)
			.sign(Algorithm.HMAC512(secretKey));
	}

	public String validateAndExtractEmailFromToken(String token) {
		try {
			return JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token).getSubject();
		} catch (TokenExpiredException e) {
			return "EXPIRE";
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
		}
	}

	public String getTokenFromHeader(HttpServletRequest request) {
		String header = request.getHeader(JwtProperties.getHEADER_STRING());
		if (header == null || !header.startsWith(JwtProperties.getTOKEN_PREFIX())) {
			return null;
		} else {
			return header.replace(JwtProperties.getTOKEN_PREFIX(), "");
		}
	}
}
