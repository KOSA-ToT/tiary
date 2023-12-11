package com.example.tiary.myPage.service;

import com.example.tiary.myPage.dto.response.ResponseMyArticleDto;
import com.example.tiary.myPage.dto.response.ResponseMyCommentDto;
import com.example.tiary.users.dto.RequestUserDto;
import com.example.tiary.myPage.dto.response.ResponseUsersDto;
import com.example.tiary.users.entity.Users;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface UserService {
    //내 정보 조회
    ResponseUsersDto readUser(Long userId);
    //이메일 수정
    Users editEmail(RequestUserDto requestUserDto, Long userId);
    //닉네임 수정
    Users editNickname(RequestUserDto requestUserDto, Long userId);
//    //이미지 경로 수정
//    Users editProfileImg(RequestUserDto requestUserDto, Long userId);

    //닉네임 중복 검사
    boolean isNicknameDuplicate(String nickname);
    //이메일 중복 검사
    boolean isEmailDuplicate(String email);
    //내 글 보기
    List<ResponseMyArticleDto> showMyArticle(Long userId);
    //내 댓글 보기
    List<ResponseMyCommentDto> showMyComment(Long userId);
    //유저 inactive하기
    Users accountCancellation(RequestUserDto requestUserDto, Long userId);
    //유저 프로필 이미지 업로드
    Users uploadProfileImg(Long userId, MultipartFile imgFile) throws IOException;;
}
