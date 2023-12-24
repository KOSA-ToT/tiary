package com.example.eureka.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Value("${security.username}")
	private String username;
	@Value("${security.password}")
	private String password;
	@Value("${security.role}")
	private String role;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder(); }
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf((auth) -> auth.disable());
		http.authorizeHttpRequests((auth) -> auth.anyRequest().authenticated()); http.httpBasic(Customizer.withDefaults());
		return http.build();
	}
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user1 = User.builder()
			.username(username) .password(bCryptPasswordEncoder().encode(password)) .roles(role)
			.build();
		return new InMemoryUserDetailsManager(user1); }
}