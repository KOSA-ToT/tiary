package com.example.tiary.global.config.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.tiary.global.config.auth.CustomAuthenticationProvider;
import com.example.tiary.global.util.CookieUtil;
import com.example.tiary.users.dto.LoginDto;
import com.example.tiary.users.entity.Users;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER =
		new AntPathRequestMatcher("/auth/login", "POST");
	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;
	TokenService tokenService;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, TokenService tokenService) {
		super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
		this.tokenService = tokenService;
		System.out.println("AuthenticationManager 객체 생성");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
		throws AuthenticationException, IOException {
		System.out.println("JwtAuthenticationFilter : 진입");

		ObjectMapper om = new ObjectMapper();
		LoginDto loginDto = om.readValue(request.getInputStream(), LoginDto.class);

		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(loginDto.getEmail(), "");
		System.out.println("JwtAuthenticationFilter : UsernamePasswordAuthenticationToken 생성 완료");

		return customAuthenticationProvider.authenticate(authenticationToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
		System.out.println("successfulAuthentication");
		Users principalDetails = (Users)authResult.getPrincipal();

		String accessToken = tokenService.createAccessToken(
			principalDetails.getId(),
			principalDetails.getEmail(),
			JwtProperties.getACCESS_TOKEN_EXPIRE_DURATION());

		String refreshToken = tokenService.createAccessToken(
			principalDetails.getId(),
			principalDetails.getEmail(),
			JwtProperties.getREFRESH_TOKEN_EXPIRE_DURATION());
		createRefreshCookie(request, response, refreshToken);

		response.addHeader(JwtProperties.getHEADER_STRING(), JwtProperties.getTOKEN_PREFIX() + accessToken);
	}

	public void createRefreshCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
		// TODO 승희 : 이 코드 필요한가? 아래 코드로 대체 가능한가?
		CookieUtil.deleteCookie(
			request,
			response,
			JwtProperties.getREFRESH_TOKEN_COOKIE_NAME());

		CookieUtil.createCookie(
			response,
			JwtProperties.getREFRESH_TOKEN_COOKIE_NAME(),
			refreshToken,
			JwtProperties.getREFRESH_TOKEN_EXPIRE_DURATION());
	}
}
