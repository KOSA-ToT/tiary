package com.example.tiary.users.dto;

import com.example.tiary.users.entity.Users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class UserDto {
	private Users users;

	private String email;

	public UserDto(Users users) {
		this.users = users;
	}

}
