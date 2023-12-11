package com.example.tiary.users.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
	private final JavaMailSender mailSender;
	private final RedisUtil redisUtil;

	@Value("${mail.username}")
	private String senderEmail;
	@Value("${spring.mail.verify-link}")
	private String verifyLink;

	public void sendMail(String receiver, String encodedKey) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		String linkButtonStyle = "display:inline-block; background:#f2f4f6; color:#4e5968; "
			+ "font-family:Toss Product Sans OTF, SF Pro KR, SF Pro Display, SF Pro Icons, -apple-system, "
			+ "BlinkMacSystemFont, Basier Square, Apple SD Gothic Neo, Roboto, Noto Sans KR, Noto Sans, "
			+ "Helvetica Neue, Helvetica, Arial, sans-serif, Apple Color Emoji, Segoe UI Emoji, Segoe UI Symbol, "
			+ "Noto Color Emoji; font-size:15px; font-weight:600; line-height:120%; margin:0; "
			+ "text-decoration:none; padding:14px 18px; border-radius:8px";

		String linkButton = "<a href=\"" + verifyLink + encodedKey + "\" "
			+ "style=\"" + linkButtonStyle + "\" target=\"_blank\">"
			+ "이메일 인증하기</a>";

		String greetingMsg = "<br>안녕하세요! T o T 입니다. <br>"
			+ "사이트를 찾아 주셔서 감사합니다. 여러분의 소중한 시간을 함께 할 수 있어 기쁩니다.<br><br><br>"
			+ "컨텐츠 이용을 위해 계정을 인증하려면 아래 버튼을 5분 이내에 클릭하세요: <br><br>";

		helper.setFrom(senderEmail);
		helper.setTo(receiver);
		helper.setSubject("[Tiary] 계정 인증을 위한 이메일 링크 🔐");
		helper.setText(greetingMsg + linkButton, true);
		mailSender.send(message);
	}

}
