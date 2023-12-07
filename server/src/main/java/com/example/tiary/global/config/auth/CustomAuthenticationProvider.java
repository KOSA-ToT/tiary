package com.example.tiary.global.config.auth;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.example.tiary.users.entity.Users;
import com.example.tiary.users.service.UserService;

public class CustomAuthenticationProvider
	implements org.springframework.security.authentication.AuthenticationProvider {

	private final UserService userService;

	public CustomAuthenticationProvider(UserService userService) {
		this.userService = userService;
	}

	// 비밀번호 없이 이메일만 체크
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = authentication.getName();

		Users users = userService.loadUserByUsername(email);

		if (users != null) {
			return new UsernamePasswordAuthenticationToken(users, null, users.getAuthorities());
		} else {
			throw new BadCredentialsException("회원 정보가 없습니다.");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
