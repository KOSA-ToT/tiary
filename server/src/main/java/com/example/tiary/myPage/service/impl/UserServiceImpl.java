package com.example.tiary.myPage.service.impl;

import com.example.tiary.article.entity.Article;
import com.example.tiary.global.exception.BusinessLogicException;
import com.example.tiary.global.exception.ExceptionCode;
import com.example.tiary.global.s3.service.S3UploadService;
import com.example.tiary.myPage.dto.response.ResponseMyArticleDto;
import com.example.tiary.myPage.dto.response.ResponseMyCommentDto;
import com.example.tiary.users.constant.UserStatus;
import com.example.tiary.users.dto.RequestUserDto;
import com.example.tiary.users.entity.Users;
import com.example.tiary.myPage.repository.UserRepository;
import com.example.tiary.myPage.service.UserService;
import com.example.tiary.myPage.dto.response.ResponseUsersDto;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final S3UploadService s3UploadService;
    public UserServiceImpl(UserRepository userRepository, S3UploadService s3UploadService){
        this.userRepository= userRepository;
        this.s3UploadService = s3UploadService;
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
    //내 글 보기
    @Transactional
    @Override
    public Page<ResponseMyArticleDto> showMyArticle(Long userId, Pageable pageable){
        Page<ResponseMyArticleDto> myArticleList= userRepository.listMyArticle(userId, pageable);
        return myArticleList;
    }
    //내 글 수 보기
    @Transactional
    @Override
    public Integer numberMyPosts(Long userId){
        Integer articleListNumber= userRepository.countArticlesById(userId);
        return articleListNumber;
    }
    //내 댓글 보기
    @Transactional
    @Override
    public Page<ResponseMyCommentDto> showMyComment(Long userId, Pageable pageable){
        Page<ResponseMyCommentDto> myCommentList= userRepository.listMyComment(userId, pageable);
        return myCommentList;
    }

    //유저 inactive만들기
    @Transactional
    @Override
    public Users accountCancellation(RequestUserDto requestUserDto, Long userId){
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));
        users.updateStatus(UserStatus.INACTIVE);
        return userRepository.save(users);
    }

    //유저 프로필 업로드
    @Transactional
    @Override
    public Users uploadProfileImg(Long userId, MultipartFile imgFile) throws IOException {
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        String storeName = UUID.randomUUID() + "-" + imgFile.getOriginalFilename();
        s3UploadService.upload(imgFile, storeName);
        users.updateProfileImg(storeName);
        return users;
    }
}
