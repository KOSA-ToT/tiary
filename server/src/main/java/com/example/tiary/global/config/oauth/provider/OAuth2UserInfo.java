package com.example.tiary.global.config.oauth.provider;

import com.example.tiary.users.constant.Provider;

public interface OAuth2UserInfo {
	Provider getProvider();

	String getProviderSub();

	String getEmail();

	String getName();
}
