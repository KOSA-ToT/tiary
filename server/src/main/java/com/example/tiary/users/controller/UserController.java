package com.example.tiary.users.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tiary.users.dto.RequestUserDto;
import com.example.tiary.users.dto.UserDto;
import com.example.tiary.users.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

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
		System.out.println("principal nickname : " + principal.getUsers().getNickname());
		System.out.println("principal email : " + principal.getUsers().getEmail());
		return "<h1>user</h1>";
	}

	@PostMapping("/join")
	public String join(@RequestBody RequestUserDto requestUserDto) {
		userService.createUser(requestUserDto);
		return "회원 가입 완료";
	}

	// 회원 가입
	@PostMapping("/signup")
	public ResponseEntity signup(@RequestBody RequestUserDto requestUserDto) {
		userService.createUser(requestUserDto);
		return ResponseEntity.ok("가입되었습니다.");
	}

	// 이메일 존재 여부 체크
	@GetMapping("/chk-email")
	public ResponseEntity checkDupEmail(@RequestParam("email") String email) {
		return userService.existsEmail(email)
			? ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용중인 이메일입니다.")
			: ResponseEntity.ok("사용 가능한 이메일입니다.");
	}

	// 닉네임 존재 여부 체크
	@GetMapping("/chk-nickname")
	public ResponseEntity<String> checkDupNickname(@RequestParam("nickname") String nickname) {
		return userService.existsNickname(nickname)
			? ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 닉네임입니다.")
			: ResponseEntity.ok("사용 가능한 닉네임입니다.");
	}

	//TODO 로그인 수정 필요
	@PostMapping("/login")
	public ResponseEntity login() {
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
