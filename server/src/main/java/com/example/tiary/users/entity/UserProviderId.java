package com.example.tiary.users.entity;

import java.io.Serializable;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.tiary.users.constant.Provider;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class UserProviderId implements Serializable {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "email", referencedColumnName = "email")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Users users;

	@Enumerated(EnumType.STRING)
	private Provider provider;

	public static UserProviderId of(Users users, Provider provider) {
		return new UserProviderId(users, provider);
	}
}
