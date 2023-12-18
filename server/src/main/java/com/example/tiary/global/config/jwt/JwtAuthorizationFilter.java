package com.example.tiary.global.config.jwt;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.example.tiary.global.exception.BusinessLogicException;
import com.example.tiary.global.exception.ExceptionCode;
import com.example.tiary.global.util.CookieUtil;
import com.example.tiary.users.dto.UserDto;
import com.example.tiary.users.entity.Users;
import com.example.tiary.users.repository.UsersRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
	private final UsersRepository usersRepository;
	private final TokenService tokenService;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UsersRepository usersRepository,
		TokenService tokenService) {
		super(authenticationManager);
		this.usersRepository = usersRepository;
		this.tokenService = tokenService;
	}

	// TODO : 민지님 authentication 객체 확인 해보기 getPrincipal()
	/*
	 * Principal 객체는 임의로 안의 값을 설정할 수 있음
	 * e.g
	 * 유저 PK id 를 넣어줄 지
	 * 유저 이름, 이메일 등등 이런거를 넣어주고
	 * 혹은 또 어떤 다른 것들을 넣어줄 수 있음
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		// Header 에서 토큰 추출
		String accessToken = tokenService.getTokenFromHeader(request);
		if (accessToken == null) {
			chain.doFilter(request, response);
			return;
		}
		try {
			// 액세스 토큰 유효성 검사 후, 만료되었으면 리프레시 토큰 검사
			String email = tokenService.validateAndExtractEmailFromToken(accessToken);
			if (email.equals("EXPIRE")) {
				String newAccessToken = renewAccessTokenIfNecessary(request);
				response.addHeader(JwtProperties.getHEADER_STRING(), JwtProperties.getTOKEN_PREFIX() + newAccessToken);
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				return;
			}
			Users user = usersRepository.findByEmail(email)
				.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
			UserDto principalDetails = new UserDto(user);

			Authentication authentication = new UsernamePasswordAuthenticationToken(
				principalDetails,
				null,
				principalDetails.getUsers().getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			chain.doFilter(request, response);
		} catch (BusinessLogicException e) {
			System.out.println("AuthorizationFilter: " + e.getMessage());
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		} catch (Exception e) {
			System.out.println("AuthorizationFilter: " + e.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	public String renewAccessTokenIfNecessary(HttpServletRequest request) {
		// 리프레시 토큰이 없으면 인증 실패, 유효하면 액세스 토큰 발급
		String refreshToken = CookieUtil.getValueFromCookie(request, JwtProperties.getREFRESH_TOKEN_COOKIE_NAME());
		String email = tokenService.validateAndExtractEmailFromToken(refreshToken);
		Users user = usersRepository.findByEmail(email)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
		return tokenService.createToken(
			user.getId(),
			user.getEmail(),
			JwtProperties.getACCESS_TOKEN_EXPIRE_DURATION());
	}
}
