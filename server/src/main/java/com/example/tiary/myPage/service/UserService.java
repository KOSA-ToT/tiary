package com.example.tiary.myPage.service;

import com.example.tiary.myPage.dto.response.ResponseMyArticleDto;
import com.example.tiary.myPage.dto.response.ResponseMyCommentDto;
import com.example.tiary.users.dto.RequestUserDto;
import com.example.tiary.myPage.dto.response.ResponseUsersDto;
import com.example.tiary.users.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    //닉네임 중복 검사
    boolean isNicknameDuplicate(String nickname);
    //내 글 보기
    Page<ResponseMyArticleDto> showMyArticle(Long userId, Pageable pageable);
    //내 댓글 보기
    Page<ResponseMyCommentDto> showMyComment(Long userId, Pageable pageable);
    //유저 inactive하기
    Users accountCancellation(RequestUserDto requestUserDto, Long userId);
    //유저 프로필 이미지 업로드
    Users uploadProfileImg(Long userId, MultipartFile imgFile) throws IOException;

    Integer numberMyPosts(Long userId);

}
