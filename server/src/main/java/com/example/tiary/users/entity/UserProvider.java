package com.example.tiary.users.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProvider {
	@EmbeddedId
	private UserProviderId userProviderId;

	private String providerSub;

	public static UserProvider of(UserProviderId userProviderId, String providerSub) {
		return new UserProvider(userProviderId, providerSub);
	}
}
