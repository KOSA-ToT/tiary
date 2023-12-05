package com.example.tiary.users.dto;

import com.example.tiary.users.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class UserDto {
	private User user;

	private String email;

	public UserDto(User user) {
		this.user = user;
	}

}
