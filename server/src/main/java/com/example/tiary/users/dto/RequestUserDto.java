package com.example.tiary.users.dto;

import com.example.tiary.users.constant.Role;
import com.example.tiary.users.entity.Users;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RequestUserDto {
	private String nickname;
	@Email
	private String email;
	@Enumerated(EnumType.STRING)
	private Role role;
	private String userPicture;
	private String userStatus;

	public Users toEntity() {
		return Users.of(
			nickname,
			email
		);
	}
}
