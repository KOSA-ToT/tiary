package com.example.tiary.global.config.oauth.provider;

import java.util.Map;

import com.example.tiary.users.constant.Provider;

public class NaverUserInfo implements OAuth2UserInfo {

	private final Map<String, Object> attributes;

	public NaverUserInfo(Map<String, Object> attributes) {
		this.attributes = (Map<String, Object>)attributes.get("response");
	}

	@Override
	public Provider getProvider() {
		return Provider.NAVER;
	}

	@Override
	public String getProviderSub() {
		return attributes.get("id").toString();
	}

	@Override
	public String getEmail() {
		return attributes.get("email").toString();
	}

	@Override
	public String getName() {
		return attributes.get("nickname").toString();
	}
}
