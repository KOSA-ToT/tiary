package com.example.tiary.myPage.service.impl;

import com.example.tiary.users.dto.RequestUserDto;
import com.example.tiary.users.entity.Users;
import com.example.tiary.myPage.repository.UserRepository;
import com.example.tiary.myPage.service.UserService;
import com.example.tiary.myPage.dto.response.ResponseUsersDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository= userRepository;
    }

    //유저 정보 조회
    @Transactional(readOnly = true)
    @Override
    public ResponseUsersDto readUser(Long userId) {
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));
        return ResponseUsersDto.from(users);
    }

    //유저 닉네임 중복 확인
    @Transactional(readOnly = true)
    @Override
    public boolean isNicknameDuplicate(String nickname){
        Optional<Users> user = userRepository.findByNickname(nickname);
        return user.isPresent();
    }

    //유저 이메일 중복 확인
    @Transactional(readOnly = true)
    @Override
    public boolean isEmailDuplicate(String email){
        Optional<Users> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    //이메일 정보 수정
    @Transactional
    @Override
    public Users editEmail(RequestUserDto requestUserDto, Long userId){
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));
        users.updateEmail(requestUserDto.getEmail());
        return userRepository.save(users);
    }
    //닉네임 수정
    @Transactional
    @Override
    public Users editNickname(RequestUserDto requestUserDto, Long userId){
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));
        users.updateNickname(requestUserDto.getNickname());
        return userRepository.save(users);
    }
    //이미지 경로 수정
    @Transactional
    @Override
    public Users editProfileImg(RequestUserDto requestUserDto, Long userId){
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));
        users.updateProfileImg(requestUserDto.getUserPicture());
        return userRepository.save(users);
    }
}
