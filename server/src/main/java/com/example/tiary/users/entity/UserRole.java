package com.example.tiary.users.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;

@Entity
public class UserRole {
	@Id
	private Long id;
	@Email
	private String email;
}
