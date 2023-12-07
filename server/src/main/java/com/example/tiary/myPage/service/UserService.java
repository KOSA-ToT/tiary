package com.example.tiary.myPage.service;


import com.example.tiary.users.dto.RequestUserDto;
import com.example.tiary.myPage.dto.response.ResponseUsersDto;
import com.example.tiary.users.entity.Users;


public interface UserService {
    //내 정보 조회
    ResponseUsersDto readUser(Long userId);
    //이메일 수정
    Users editEmail(RequestUserDto requestUserDto, Long userId);
    //닉네임 수정
    Users editNickname(RequestUserDto requestUserDto, Long userId);
    //이미지 경로 수정
    Users editProfileImg(RequestUserDto requestUserDto, Long userId);
    //닉네임 중복 검사
    boolean isNicknameDuplicate(String nickname);
    //이메일 중복 검사
    boolean isEmailDuplicate(String email);
}