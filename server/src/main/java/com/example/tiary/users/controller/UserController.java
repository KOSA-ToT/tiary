package com.example.tiary.users.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tiary.global.config.jwt.JwtProperties;
import com.example.tiary.global.config.jwt.TokenService;
import com.example.tiary.users.dto.EmailSendDto;
import com.example.tiary.users.dto.RequestUserDto;
import com.example.tiary.users.dto.UserDto;
import com.example.tiary.users.service.EmailService;
import com.example.tiary.users.service.RedisUtil;
import com.example.tiary.users.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	private final TokenService tokenService;
	private final EmailService emailService;
	private final RedisUtil redisUtil;

	@GetMapping("/home")
	public String home() {
		return "<h1>home</h1>";
	}

	// 매니저, 어드민 접근 가능 TEST
	@GetMapping("/user")
	// @PreAuthorize("hasAnyRole('WRITER', 'ADMIN')")
	public ResponseEntity<Object> noUser(Authentication authentication) {
		System.out.println(authentication.getPrincipal());
		UserDto principal = (UserDto)authentication.getPrincipal();
		Map<String, Object> response = new HashMap<>();
		response.put("id", principal.getUsers().getId());
		response.put("nickname", principal.getUsers().getNickname());
		response.put("email", principal.getUsers().getEmail());
		response.put("picture", principal.getUsers().getUserPicture());
		response.put("role", principal.getUsers().getRole());
		return ResponseEntity.ok(response);
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
	public ResponseEntity checkDupNickname(@RequestParam("nickname") String nickname) {
		return userService.existsNickname(nickname)
			? ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 닉네임입니다.")
			: ResponseEntity.ok("사용 가능한 닉네임입니다.");
	}

	// 인증 이메일 전송
	@PostMapping("/send-email")
	public ResponseEntity sendEmail(@RequestBody EmailSendDto emailSendDto) throws MessagingException {
		String encodedKey = redisUtil.setDataExpire(emailSendDto.getEmail());
		emailService.sendMail(emailSendDto, encodedKey);
		return ResponseEntity.ok("이메일 전송이 완료되었습니다. 5분 이내 인증해주세요.");
	}
	// 거절 이메일 전송
	@PostMapping("/reject-email")
	public ResponseEntity sendRejectEmail(@RequestBody EmailSendDto emailSendDto) throws MessagingException {
		String encodedKey = redisUtil.setDataExpire(emailSendDto.getEmail());
		emailService.sendRejectMail(emailSendDto, encodedKey);
		return ResponseEntity.ok("거절 이메일 전송이 완료되었습니다.");
	}
	@PostMapping("/accept-email")
	public ResponseEntity sendAcceptEmail(@RequestBody EmailSendDto emailSendDto) throws MessagingException {
		String encodedKey = redisUtil.setDataExpire(emailSendDto.getEmail());
		emailService.sendAcceptMail(emailSendDto, encodedKey);
		return ResponseEntity.ok("승인 이메일 전송이 완료되었습니다.");
	}
	// 인증 확인
	@GetMapping("/verify-email")
	public ResponseEntity verifiedEmail(@RequestParam("link") String encodedKey, @RequestParam("task") String task) {
		String result = redisUtil.getData(encodedKey);

		redisUtil.deleteData(encodedKey);
		Map<String, String> response = new HashMap<>();
		response.put("task", task);
		response.put("email", result);
		response.put("accepted", "이메일 인증 완료");
		return result != null ? ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
			: ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("제한시간이 초과되었습니다.");
	}

	@PostMapping("/oauth2/access-token")
	public ResponseEntity<Object> getAccessToken(@CookieValue("refreshToken") String refreshToken,
		HttpServletResponse response) {
		// TODO 승희: 반복되는 코드 처리
		String email = tokenService.validateAndExtractEmailFromToken(refreshToken);
		String accessToken = tokenService.createToken(
			userService.loadUserByUsername(email).getId(),
			email,
			JwtProperties.getACCESS_TOKEN_EXPIRE_DURATION());
		response.addHeader(JwtProperties.getHEADER_STRING(), JwtProperties.getTOKEN_PREFIX() + accessToken);
		return ResponseEntity.status(HttpStatus.CREATED).body("accessToken 발급 완료");
	}
}
