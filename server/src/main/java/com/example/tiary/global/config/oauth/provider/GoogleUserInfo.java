package com.example.tiary.global.config.oauth.provider;

import java.util.Map;

import com.example.tiary.users.constant.Provider;

public class GoogleUserInfo implements OAuth2UserInfo {

	private final Map<String, Object> attributes;

	public GoogleUserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public Provider getProvider() {
		return Provider.GOOGLE;
	}

	@Override
	public String getProviderSub() {
		return (String)attributes.get("sub");
	}

	@Override
	public String getEmail() {
		return (String)attributes.get("email");
	}

	@Override
	public String getName() {
		return (String)attributes.get("name");
	}
}
