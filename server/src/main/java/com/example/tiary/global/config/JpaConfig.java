package com.example.tiary.global.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.tiary.users.dto.UserDto;
import com.example.tiary.users.entity.Users;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@EnableJpaAuditing
@RequiredArgsConstructor
@Configuration
@Slf4j
public class JpaConfig {
	private final HttpSession session;
	@Bean
	public AuditorAware<String> auditorAware() {
		return () -> Optional.ofNullable(SecurityContextHolder.getContext())
			.map(SecurityContext::getAuthentication)
			.filter(Authentication::isAuthenticated)
			.map(Authentication::getPrincipal)
			.map(principal -> principal instanceof UserDto ? (UserDto)principal : null)
			.map(UserDto.class::cast)
			.map(UserDto::getUsers)
			.map(Users.class::cast)
			.map(Users::getNickname)
			.orElse("anonymousUser").describeConstable();
	}
}
