package com.example.tiary.users.service;

import java.time.Duration;
import java.util.Random;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.example.tiary.global.LinkEncryptionUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RedisUtil {
	private final StringRedisTemplate template;

	public String getData(String encodedKey) {
		ValueOperations<String, String> valueOperations = template.opsForValue();
		String decodedKey = LinkEncryptionUtil.decodeKey(encodedKey);
		return valueOperations.get(decodedKey); // value 값인 email 반환
	}

	public String setDataExpire(String email) {
		ValueOperations<String, String> valueOperations = template.opsForValue();
		// 랜덤 값 생성
		Random random = new Random();
		int randomNumber = random.nextInt(1_000_000_000); // 범위 설정
		String key = String.format("%09d", randomNumber); // 키값
		String encodedKey = LinkEncryptionUtil.generateEncodedKey(key); // 6자리
		// 유효기간 설정
		Duration expireDuration = Duration.ofMinutes(5);
		valueOperations.set(key, email, expireDuration); // 키값으로 저장
		return encodedKey;
	}

	public void deleteData(String encodedKey) {
		template.delete(LinkEncryptionUtil.decodeKey(encodedKey));
	}
}
