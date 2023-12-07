package com.example.tiary.users.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
	private final JavaMailSender mailSender;
	private final RedisUtil redisUtil;

	@Value("${mail.username}")
	private String senderEmail;

	//TODO localhost 수정필요
	public void sendMail(String receiver, String encodedKey) {
		String verifyLink = "http://localhost:8088/auth/verify-email?link=" + encodedKey;

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(senderEmail);
		message.setTo(receiver);
		message.setSubject("[Tiary] 계정 인증을 위한 이메일 링크 🔐");
		message.setText("\n계정을 인증하려면 아래 링크를 5분 이내에 클릭하세요: \n\n" + verifyLink);
		mailSender.send(message);
	}

}
