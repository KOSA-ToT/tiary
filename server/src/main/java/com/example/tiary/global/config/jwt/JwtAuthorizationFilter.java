package com.example.tiary.global.config.jwt;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.tiary.users.dto.UserDto;
import com.example.tiary.users.entity.Users;
import com.example.tiary.users.repository.UsersRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
	private final UsersRepository usersRepository;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
		UsersRepository usersRepository) {
		super(authenticationManager);
		this.usersRepository = usersRepository;
	}

	//TODO : 민지님 authentication 객체 확인 해보기 getPrincipal()
	/*
	Principal 객체는 임의로 안의 값을 설정할 수 있음
	e.g
	유저 PK id 를 넣어줄 지
	유저 이름, 이메일 등등 이런거를 넣어주고
	혹은 또 어떤 다른 것들을 넣어줄 수 있음
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {
		String header = request.getHeader(JwtProperties.HEADER_STRING);
		if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}
		System.out.println("header : " + header);
		String token = header.replace(JwtProperties.TOKEN_PREFIX, "");
		String email =
			JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
				.getClaim("email").asString();
		System.out.println("email : " + email);
		if (email != null) {
			Users user = usersRepository.findUserByEmail(email);
			System.out.println("User : " + user);
			UserDto principalDetails = new UserDto(user);
			System.out.println(principalDetails.getUsers().getAuthorities());
			Authentication authentication =
				new UsernamePasswordAuthenticationToken(
					principalDetails,
					null,
					principalDetails.getUsers().getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		chain.doFilter(request, response);
	}

}
