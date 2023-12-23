package com.example.tiary.global.config.auth;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.example.tiary.global.exception.BusinessLogicException;
import com.example.tiary.global.exception.ExceptionCode;
import com.example.tiary.users.constant.UserStatus;
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
		if(users.getUserStatus().equals(UserStatus.INACTIVE)) {
			throw new BusinessLogicException(ExceptionCode.USER_EXIST_INACTIVE);
		}
		return new UsernamePasswordAuthenticationToken(users, null, users.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
