package com.example.tiary.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.tiary.global.config.auth.CustomAuthenticationEntryPoint;
import com.example.tiary.global.config.auth.CustomAuthenticationProvider;
import com.example.tiary.global.config.jwt.JwtAuthenticationFilter;
import com.example.tiary.global.config.jwt.JwtAuthorizationFilter;
import com.example.tiary.global.config.jwt.TokenService;
import com.example.tiary.global.config.oauth.OAuth2FailureHandler;
import com.example.tiary.global.config.oauth.OAuth2SuccessHandler;
import com.example.tiary.global.config.oauth.OAuth2UserCustomService;
import com.example.tiary.users.repository.UsersRepository;
import com.example.tiary.users.service.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // @PreAuthorize, @PostAuthorize, @Secured 등과 같은 메소드 수준의 보안 어노테이션을 사용 가능하게 만들어줌
public class SpringSecurityConfig {
	private final UsersRepository userRepository;
	private final UserService userService;
	private final AuthenticationConfiguration authenticationConfiguration;
	private final CorsConfig corsConfig;
	private final TokenService tokenService;
	private final OAuth2UserCustomService oAuth2UserCustomService;

	public SpringSecurityConfig(UsersRepository userRepository, UserService userService, TokenService tokenService,
		AuthenticationConfiguration authenticationConfiguration, CorsConfig corsConfig,
		OAuth2UserCustomService oAuth2UserCustomService) {
		this.userRepository = userRepository;
		this.userService = userService;
		this.authenticationConfiguration = authenticationConfiguration;
		this.corsConfig = corsConfig;
		this.tokenService = tokenService;
		this.oAuth2UserCustomService = oAuth2UserCustomService;
	}

	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring()
			.requestMatchers(new AntPathRequestMatcher("/h2/**"))
			.requestMatchers(new AntPathRequestMatcher("/comment/guest/**"))
			.requestMatchers(new AntPathRequestMatcher("/category/**"))
			.requestMatchers(new AntPathRequestMatcher("/likes/**"));
	}

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// spring boot 3.1부터 deprecated 된 코드들 존재
		http
			.headers().frameOptions().sameOrigin()
			.and()
			.addFilter(corsConfig.corsFilter())
			.formLogin(FormLoginConfigurer::disable)
			.csrf(CsrfConfigurer::disable)
			.httpBasic(HttpBasicConfigurer::disable)
			.sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.addFilterBefore(jwtAuthenticationFilter(),
				UsernamePasswordAuthenticationFilter.class)
			.addFilter(jwtAuthorizationFilter())
			.addFilterBefore(anonymousAuthenticationFilter(), JwtAuthenticationFilter.class)
			.authorizeHttpRequests(authorize -> {
				authorize
					.requestMatchers(new AntPathRequestMatcher("/auth/**")).permitAll();
				authorize.requestMatchers(new AntPathRequestMatcher("/article/**")).permitAll();
				authorize.requestMatchers(new AntPathRequestMatcher("/category/**")).permitAll();
				authorize.requestMatchers(new AntPathRequestMatcher("/comment/**")).permitAll();
				authorize.requestMatchers(new AntPathRequestMatcher("/users/**")).permitAll();
				authorize.requestMatchers(new AntPathRequestMatcher("/admin/**")).permitAll();
				authorize.requestMatchers(new AntPathRequestMatcher("/**")).permitAll();
				authorize.anyRequest().authenticated();
			})
			.exceptionHandling(e -> e
				.authenticationEntryPoint(customAuthenticationEntryPoint()));
		http.oauth2Login(oauth2 -> oauth2
			.failureHandler(oauth2FailureHandler())
			.successHandler(oauth2SuccessHandler())
			.userInfoEndpoint(userInfo -> userInfo
				.userService(oAuth2UserCustomService)));
		return http.build();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		System.out.println("인증 필터 등록");
		return new JwtAuthenticationFilter(authenticationManagerBean(), tokenService);
	}

	@Bean
	public JwtAuthorizationFilter jwtAuthorizationFilter() throws Exception {
		System.out.println("인가 필터 등록");
		return new JwtAuthorizationFilter(authenticationManagerBean(), userRepository, tokenService);
	}

	@Bean
	public AnonymousAuthenticationFilter anonymousAuthenticationFilter() {
		return new AnonymousAuthenticationFilter("anonymousUser");
	}

	@Bean
	public CustomAuthenticationProvider customAuthenticationProvider() {
		return new CustomAuthenticationProvider(userService);
	}

	@Bean
	public OAuth2SuccessHandler oauth2SuccessHandler() {
		return new OAuth2SuccessHandler(tokenService, userRepository);
	}

	@Bean
	public OAuth2FailureHandler oauth2FailureHandler() {
		return new OAuth2FailureHandler();
	}

	@Bean
	public CustomAuthenticationEntryPoint customAuthenticationEntryPoint() {
		return new CustomAuthenticationEntryPoint();
	}
}
