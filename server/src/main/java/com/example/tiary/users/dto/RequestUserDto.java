package com.example.tiary.users.dto;

import org.hibernate.validator.constraints.Length;

import com.example.tiary.users.constant.Role;
import com.example.tiary.users.constant.UserStatus;
import com.example.tiary.users.entity.Users;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
	@Column(nullable = false, length = 20)
	@Length(min = 1, max = 20, message = "이름은 20자 이하여야 합니다.")
	@NotBlank(message = "이름은 필수 값입니다.")
	private String nickname;

	@Column(nullable = false, length = 60)
	@Length(min = 1, max = 60, message = "이메일은 60자 이하여야 합니다.")
	@NotBlank(message = "이메일은 필수 값입니다.")
	@Email
	private String email;

	@Enumerated(EnumType.STRING)
	private Role role;

	private String userPicture;

	@Enumerated(EnumType.STRING)
	private UserStatus userStatus;

	public Users toEntity() {
		return Users.of(
			nickname,
			email
		);
	}

	public Users toSignUpEntity() {
		return Users.signUp(
			nickname,
			email
		);
	}
}
