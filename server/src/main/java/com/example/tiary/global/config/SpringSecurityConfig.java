package com.example.tiary.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.tiary.global.config.auth.CustomAuthenticationProvider;
import com.example.tiary.global.config.jwt.JwtAuthenticationFilter;
import com.example.tiary.global.config.jwt.JwtAuthorizationFilter;
import com.example.tiary.users.repository.UserRepository;
import com.example.tiary.users.service.UserService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	private final UserRepository userRepository;
	private final UserService userService;
	private final AuthenticationConfiguration authenticationConfiguration;
	private final CorsConfig corsConfig;

	public SpringSecurityConfig(UserRepository userRepository,
		UserService userService, AuthenticationConfiguration authenticationConfiguration, CorsConfig corsConfig) {
		this.userRepository = userRepository;
		this.userService = userService;
		this.authenticationConfiguration = authenticationConfiguration;
		this.corsConfig = corsConfig;
	}

	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// spring boot 3.1부터 deprecated 된 코드들 존재
		http
			.addFilter(corsConfig.corsFilter())
			.csrf(CsrfConfigurer::disable)
			.httpBasic(HttpBasicConfigurer::disable)
			.sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.addFilterBefore(jwtAuthenticationFilter(),
				UsernamePasswordAuthenticationFilter.class)
			.addFilter(jwtAuthorizationFilter())
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/home", "/join", "/login", "/user").permitAll()
				.anyRequest().authenticated());

		return http.build();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		System.out.println("인증 필터 등록");
		return new JwtAuthenticationFilter(authenticationManagerBean());
	}

	@Bean
	public JwtAuthorizationFilter jwtAuthorizationFilter() throws Exception {
		System.out.println("인가 필터 등록");
		return new JwtAuthorizationFilter(authenticationManagerBean(), userRepository);
	}

	@Bean
	public CustomAuthenticationProvider customAuthenticationProvider() {
		return new CustomAuthenticationProvider(userService);
	}
}
