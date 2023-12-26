package com.example.tiary.users.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.tiary.users.dto.EmailSendDto;

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

	public void sendMail(EmailSendDto emailSendDto, String encodedKey) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		String linkButton = getString(encodedKey, emailSendDto.getTask());

		String greetingMsg = "<br>안녕하세요! T o T 입니다.<br>"
			+ "사이트를 찾아 주셔서 감사합니다. 여러분의 소중한 시간을 함께 할 수 있어 기쁩니다.<br><br>"
			+ "컨텐츠 이용을 위해 계정을 인증하려면 아래 버튼을 5분 이내에 클릭하세요: <br><br>";

		helper.setFrom(senderEmail);
		helper.setTo(emailSendDto.getEmail());
		helper.setSubject("[Tiary] 계정 인증을 위한 이메일 링크 🔐");
		helper.setText(greetingMsg + linkButton, true);
		mailSender.send(message);
	}
	public void sendRejectMail(EmailSendDto emailSendDto, String encodedKey) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		String linkButton = getString(encodedKey, emailSendDto.getTask());

		String greetingMsg = "<br>안녕하세요! T o T 입니다.<br>"
				+ "작가 신청을 하셨으나, 운영진의 검토 결과 신청 기준을 충족하지 않아 신청이 반려되었습니다.<br><br>"
				+ "신청기준을 충족하신 뒤에 다시 신청해주시길 바랍니다. <br><br>";

		helper.setFrom(senderEmail);
		helper.setTo(emailSendDto.getEmail());
		helper.setSubject("[Tiary] 작가 신청이 거절되었습니다 😥");
		helper.setText(greetingMsg, true);
		mailSender.send(message);
	}
	public void sendAcceptMail(EmailSendDto emailSendDto, String encodedKey) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		String linkButton = getString(encodedKey, emailSendDto.getTask());

		String greetingMsg = "<br>안녕하세요! T o T 입니다.<br>"
				+ "작가 신청에 대한 검토가 완료되어, Tiary의 작가로 선정되셨습니다.<br><br>"
				+ "작가로서의 성공적인 활동을 기대하고 있겠습니다. 감사합니다.<br><br>";

		helper.setFrom(senderEmail);
		helper.setTo(emailSendDto.getEmail());
		helper.setSubject("[Tiary] 작가 신청이 승인되었습니다 🥳");
		helper.setText(greetingMsg, true);
		mailSender.send(message);
	}

	private String getString(String encodedKey, String task) {
		String linkButtonStyle = "display:inline-block; background:#f2f4f6; color:#4e5968; "
			+ "font-family:Toss Product Sans OTF, SF Pro KR, SF Pro Display, SF Pro Icons, -apple-system, "
			+ "BlinkMacSystemFont, Basier Square, Apple SD Gothic Neo, Roboto, Noto Sans KR, Noto Sans, "
			+ "Helvetica Neue, Helvetica, Arial, sans-serif, Apple Color Emoji, Segoe UI Emoji, Segoe UI Symbol, "
			+ "Noto Color Emoji; font-size:15px; font-weight:600; line-height:120%; margin:0; "
			+ "text-decoration:none; padding:14px 18px; border-radius:8px";

		return "<a href=\"" + verifyLink + encodedKey + "&task=" + task + "\" "
			+ "style=\"" + linkButtonStyle + "\" target=\"_blank\">"
			+ "이메일 인증하기</a>";
	}

}
