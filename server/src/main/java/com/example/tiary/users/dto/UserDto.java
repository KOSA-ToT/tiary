package com.example.tiary.users.dto;

import com.example.tiary.users.entity.Users;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserDto {
	private final Users users;

	public UserDto(Users users) {
		this.users = users;
	}

}
