package com.example.tiary.global.config.oauth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.tiary.global.config.jwt.JwtProperties;
import com.example.tiary.global.config.jwt.TokenService;
import com.example.tiary.global.exception.BusinessLogicException;
import com.example.tiary.global.exception.ExceptionCode;
import com.example.tiary.global.util.CookieUtil;
import com.example.tiary.users.entity.Users;
import com.example.tiary.users.repository.UsersRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

	@Value("${spring.security.oauth2.success-redirect}")
	private String redirectUrl;
	private final TokenService tokenService;
	private final UsersRepository usersRepository;

	public OAuth2SuccessHandler(TokenService tokenService, UsersRepository usersRepository) {
		this.tokenService = tokenService;
		this.usersRepository = usersRepository;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {

		OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
		Users user = usersRepository.findByEmail((String)oAuth2User.getAttributes().get("email"))
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

		// Refresh Token 생성 및 Cookie에 저장
		//TODO 승희 : 기존 refresh token 고려, 반복 코드 처리
		String refreshToken = tokenService.createToken(
			user.getId(),
			user.getEmail(),
			JwtProperties.getREFRESH_TOKEN_EXPIRE_DURATION());
		CookieUtil.createCookie(
			response,
			JwtProperties.getREFRESH_TOKEN_COOKIE_NAME(),
			refreshToken,
			JwtProperties.getREFRESH_TOKEN_EXPIRE_DURATION());

		response.setStatus(HttpServletResponse.SC_OK);
		response.sendRedirect(redirectUrl);
	}
}
