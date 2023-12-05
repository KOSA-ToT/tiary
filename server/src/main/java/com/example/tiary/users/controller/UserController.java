package com.example.tiary.users.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.tiary.users.constant.Role;
import com.example.tiary.users.dto.UserDto;
import com.example.tiary.users.entity.User;
import com.example.tiary.users.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserRepository userRepository;

	@GetMapping("/home")
	public String home() {
		return "<h1>home</h1>";
	}

	// 유저, 매니저, 어드민 접근 가능
	@GetMapping("/user")
	@PreAuthorize("hasAnyRole('USER', 'WRITER', 'ADMIN')")
	public String user(Authentication authentication) {
		System.out.println(authentication.getPrincipal());
		UserDto principal = (UserDto)authentication.getPrincipal();
		System.out.println("principal nickname : " + principal.getUser().getNickname());
		System.out.println("principal email : " + principal.getUser().getEmail());
		return "<h1>user</h1>";
	}

	@PostMapping("/join")
	public String join(@RequestBody User user) {
		user.setRole(Role.USER);
		userRepository.save(user);
		return "회원 가입 완료";
	}

	@PostMapping("/login")
	public String login() {
		return "토큰 발행 완료";
	}
}
