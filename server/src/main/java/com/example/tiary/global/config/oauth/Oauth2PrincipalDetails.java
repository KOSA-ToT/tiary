package com.example.tiary.global.config.oauth;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.example.tiary.global.config.oauth.provider.OAuth2UserInfo;
import com.example.tiary.global.exception.BusinessLogicException;
import com.example.tiary.global.exception.ExceptionCode;
import com.example.tiary.users.repository.UsersRepository;

import lombok.Getter;

@Getter
public class Oauth2PrincipalDetails implements OAuth2User {
	private OAuth2UserInfo userInfo;
	private UsersRepository usersRepository;

	public Oauth2PrincipalDetails(OAuth2UserInfo userInfo, UsersRepository usersRepository) {
		this.userInfo = userInfo;
		this.usersRepository = usersRepository;
	}

	@Override
	public Map<String, Object> getAttributes() {
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("sub", userInfo.getProviderSub());
		attributes.put("email", userInfo.getEmail());
		attributes.put("name", userInfo.getName());
		return attributes;
	}

	@Override
	public String getName() {
		return userInfo.getName();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return usersRepository.findByEmail(userInfo.getEmail())
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND))
			.getAuthorities();
	}
}
