package com.example.tiary.global.config.oauth;

import java.util.Optional;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tiary.global.config.oauth.provider.GoogleUserInfo;
import com.example.tiary.global.config.oauth.provider.NaverUserInfo;
import com.example.tiary.global.config.oauth.provider.OAuth2UserInfo;
import com.example.tiary.users.entity.UserProvider;
import com.example.tiary.users.entity.UserProviderId;
import com.example.tiary.users.entity.Users;
import com.example.tiary.users.repository.UserProviderRepository;
import com.example.tiary.users.repository.UsersRepository;

@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService {
	private final UsersRepository usersRepository;
	private final UserProviderRepository userProviderRepository;

	public OAuth2UserCustomService(UsersRepository usersRepository, UserProviderRepository userProviderRepository) {
		this.usersRepository = usersRepository;
		this.userProviderRepository = userProviderRepository;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		OAuth2UserInfo userInfo = null; // 유저 정보를 담을 객체
		String provider = userRequest.getClientRegistration().getRegistrationId(); // OAuth2 공급자

		switch (provider) {
			case "google" -> userInfo = new GoogleUserInfo(oAuth2User.getAttributes());
			case "naver" -> userInfo = new NaverUserInfo(oAuth2User.getAttributes());
			default -> throw new OAuth2AuthenticationException(new OAuth2Error("존재하지 않는 OAuth2 공급자입니다"));
		}
		loginAndSignUp(userInfo);
		return new Oauth2PrincipalDetails(userInfo, usersRepository);
	}

	private void loginAndSignUp(OAuth2UserInfo userInfo)
		throws OAuth2AuthenticationException {

		// TODO 승희: 예외 처리 추가, 로직 수정
		Optional<Users> user = usersRepository.findByEmail(userInfo.getEmail());
		try {
			if (user.isPresent()) {
				UserProviderId userProviderId = UserProviderId.of(user.get(), userInfo.getProvider());
				Optional<UserProvider> userProvider = userProviderRepository.findByUserProviderId(userProviderId);
				if (userProvider.isPresent()) {
					// 로그인
					System.out.println("로그인");
				} else {
					// OAuth2 회원가입
					System.out.println("OAuth2 회원가입");
					UserProvider newUserProvider = UserProvider.of(userProviderId, userInfo.getProviderSub());
					userProviderRepository.save(newUserProvider);
				}
			} else {
				// 회원가입
				// TODO 승희: 닉네임 중복 검사, +1, +2 등등 붙여주기
				System.out.println("회원가입");
				Users users = usersRepository.save(Users.signUp(userInfo.getName(), userInfo.getEmail()));
				UserProviderId userProviderId = UserProviderId.of(users, userInfo.getProvider());
				UserProvider userProvider = UserProvider.of(userProviderId, userInfo.getProviderSub());
				userProviderRepository.save(userProvider);
			}
		} catch (Exception e) {
			throw new OAuth2AuthenticationException(new OAuth2Error("회원 가입에 실패했습니다"));
		}
	}
}

