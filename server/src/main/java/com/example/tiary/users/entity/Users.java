package com.example.tiary.users.entity;

import java.util.Collection;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.tiary.users.constant.Role;
import com.example.tiary.users.constant.UserStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 20, unique = true)
	@Length(min = 1, max = 20, message = "이름은 20자 이하여야 합니다.")
	@NotBlank(message = "이름은 필수 값입니다.")
	private String nickname;

	@Column(nullable = false, length = 60,unique = true)
	@Length(min = 1, max = 60, message = "이메일은 60자 이하여야 합니다.")
	@NotBlank(message = "이메일은 필수 값입니다.")
	@Email
	private String email;

	@Enumerated(EnumType.STRING)
	private Role role;

	private String userPicture;

	@Enumerated(EnumType.STRING)
	private UserStatus userStatus;

	public static Users of(String nickname, String email) {
		return new Users(null, nickname, email, null, null, null);
	}

	public static Users signUp(String nickname, String email) {
		return new Users(null, nickname, email, Role.USER, null, UserStatus.ACTIVE);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(Role.USER.name()));
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return this.nickname;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	public void updateEmail(String email) {
		this.email = email;
	}
	public void updateNickname(String nickname) {
		this.nickname = nickname;
	}
	public void updateProfileImg(String userPicture) {
		this.userPicture = userPicture;
	}
	public void updateStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}
}
