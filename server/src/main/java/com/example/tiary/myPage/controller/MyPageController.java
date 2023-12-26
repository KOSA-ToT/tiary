package com.example.tiary.myPage.controller;
//import com.example.tiary.myPage.service.UserService;
import com.example.tiary.article.dto.request.RequestArticleDto;
import com.example.tiary.article.service.ArticleLikesService;
import com.example.tiary.article.service.ArticleService;
import com.example.tiary.users.dto.RequestUserDto;
import com.example.tiary.myPage.service.SubscribeService;
import com.example.tiary.myPage.service.UserService;
import com.example.tiary.users.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.tiary.article.service.ArticleService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/users")
public class MyPageController {

    private final UserService userService;
    private final SubscribeService subscribeService;
    private final ArticleService articleService;

    public MyPageController(UserService userService, SubscribeService subscribeService, ArticleService articleService) {
        this.userService = userService;
        this.subscribeService = subscribeService;
        this.articleService =articleService;
    }


    //내 정보 보기
    @GetMapping("/{userId}")
    public ResponseEntity checkInfo(@PathVariable("userId") Long userId){
        try{
            return ResponseEntity.ok(userService.readUser(userId));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("내 정보 조회에 실패했습니다.");
        }
    }
//    //이메일로 유저 찾기
//    @GetMapping("/{email}")
//    public ResponseEntity emailInfo(@PathVariable("email") String email){
//        try{
//            return ResponseEntity.ok(userService.readUserByEmail(email));
//        }catch(Exception e){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("내 정보 조회에 실패했습니다.");
//        }
//    }

    //내 글 보기
    @GetMapping("/{userId}/posts")
    public ResponseEntity viewMyPosts(@PathVariable("userId") Long userId,Pageable pageable){
        try{
            Pageable fixedPageable = PageRequest.of(pageable.getPageNumber(), 4, pageable.getSort());
            return ResponseEntity.ok(userService.showMyArticle(userId,fixedPageable));
        }catch(Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("내 글 조회에 실패했습니다.");
        }
    }
    //내 글 수 보기
    @GetMapping("/{userId}/postsNumber")
    public ResponseEntity numberMyPosts(@PathVariable("userId") Long userId,@AuthenticationPrincipal UserDto user){
        try{
            return ResponseEntity.ok(userService.numberMyPosts(userId));
        }catch(Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("글 조회에 실패했습니다.");
        }
    }
//    //내 글 보기
//    @GetMapping("/{userId}/posts")
//    public ResponseEntity viewMyPosts(@PathVariable("userId") Long userId){
//        try{
//            return ResponseEntity.ok(userService.showMyArticle(userId));
//        }catch(Exception e){
//            log.error(e.getMessage());
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("내 글 조회에 실패했습니다.");
//        }
//    }
//    //내 댓글 보기
//    @GetMapping("/{userId}/comments")
//    public ResponseEntity viewMyComments(@PathVariable("userId") Long userId){
//        try{
//            return ResponseEntity.ok(userService.showMyComment(userId));
//        }catch(Exception e){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("내 댓글 조회에 실패했습니다.");
//        }
//    }
    //내 댓글 보기
    @GetMapping("/{userId}/comments")
    public ResponseEntity viewMyComments(@PathVariable("userId") Long userId,Pageable pageable){
        try{
            Pageable fixedPageable = PageRequest.of(pageable.getPageNumber(), 4, pageable.getSort());
            return ResponseEntity.ok(userService.showMyComment(userId,fixedPageable));
        }catch(Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("내 댓글 조회에 실패했습니다.");
        }
    }
//    //게시물 수정
//    @PatchMapping(value = "/article/{userId}")
//    public ResponseEntity patchArticle(@PathVariable("userId") Long userId,
//                                       @RequestBody RequestUserDto requestUserDto,
//                                       @AuthenticationPrincipal UserDto user) throws IOException {
//        return new ResponseEntity<>(
//                userService.editNicknameInArticle(user.getUsers().getId(), articleId, requestArticleDto, multipartFileList),
//                HttpStatus.OK);
//    }
//    //내 정보 수정(이메일)
//    @PatchMapping("/{userId}/updateEmail")
//    public ResponseEntity updateEmail(@PathVariable("userId") Long userId, @RequestBody RequestUserDto requestUserDto){
//        try{
//            if (userService.isEmailDuplicate(requestUserDto.getEmail())){
//                return new ResponseEntity<>("이미 존재하는 이메일입니다",HttpStatus.CONFLICT);
//            }else{
//                return new ResponseEntity<>(userService.editEmail(requestUserDto,userId),HttpStatus.RESET_CONTENT);
//            }
////            return ResponseEntity.created(URI.create("/users/" + userId)).body("수정완료했습니다.");
//        }catch(Exception e){
//            log.error("에러 : ",e);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("이메일 수정에 실패했습니다.");
//        }
//    }

    //내 정보 수정(닉네임)
    @PatchMapping("/{userId}/updateNickname")
    public ResponseEntity updateNickName(@PathVariable("userId") Long userId, @RequestBody RequestUserDto requestUserDto,@AuthenticationPrincipal UserDto user){
        try{
            System.out.println("user.getUsers().getId() : "+user.getUsers().getId());
            if (userService.isNicknameDuplicate(requestUserDto.getNickname())){
                return new ResponseEntity<>("이미 존재하는 닉네임입니다",HttpStatus.CONFLICT);
            }else{
                return new ResponseEntity<>(userService.editNickname(requestUserDto,user.getUsers().getId()),HttpStatus.RESET_CONTENT);
            }
//            return ResponseEntity.created(URI.create("/users/"+userId)).body("수정완료했습니다.");

        }catch(Exception e){
            log.error("에러 : ",e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("닉네임 수정에 실패했습니다.");
        }
    }

    //구독자 보기 (닉네임,프로필 이미지)
    @GetMapping("/{userId}/subscriber")
    public ResponseEntity listSubscriber(@PathVariable("userId") Long writerId,@AuthenticationPrincipal UserDto user){
        try{
            return ResponseEntity.ok(subscribeService.readSubscriber(writerId));
        }catch(Exception e){
            log.error("에러 : "+e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("구독자 조회에 실패했습니다.");
        }
    }

    //구독 작가 보기
    @GetMapping("/{userId}/subscribedWriter")
    public ResponseEntity listSubscribedWriter(@PathVariable("userId") Long userId,@AuthenticationPrincipal UserDto user){
        try{
            return ResponseEntity.ok(subscribeService.readsubscribedWriter(userId));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("구독 작가 조회에 실패했습니다.");
        }
    }

    //유저 INACTIVE 상태 만들기
    @PatchMapping("/{userId}/inactive")
    public ResponseEntity inactiveUser(@PathVariable("userId") Long userId, @RequestBody RequestUserDto requestUserDto,@AuthenticationPrincipal UserDto user){
        try{
            return new ResponseEntity<>(userService.accountCancellation(requestUserDto,userId),HttpStatus.RESET_CONTENT);
        }catch(Exception e){
            log.error("에러 : ",e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("탈퇴에 실패했습니다.");
        }
    }
//    //프로필 이미지 등록
//    @PostMapping(value = "/{userId}/uploadProfileImg", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity uploadProfilImg(@PathVariable("userId") Long userId,
//                                          @RequestParam(value="profileImg") MultipartFile imgFile,
//                                          @AuthenticationPrincipal UserDto user) throws
////        IOException {
////            return new ResponseEntity<>(userService.uploadProfileImg(user.getUsers().getId(), imgFile),
////                    HttpStatus.CREATED);
//        IOException {
//            return new ResponseEntity<>(userService.uploadProfileImg(userId, imgFile),
//                    HttpStatus.CREATED);
//    }
    //프로필 이미지 등록
    @PostMapping(value = "/{userId}/uploadProfileImg", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity uploadProfilImg(@PathVariable("userId") Long userId,
                                          @RequestParam(value="profileImg") MultipartFile imgFile) throws
    //        IOException {
    //            return new ResponseEntity<>(userService.uploadProfileImg(user.getUsers().getId(), imgFile),
    //                    HttpStatus.CREATED);
            IOException {
        return new ResponseEntity<>(userService.uploadProfileImg(userId, imgFile),
                HttpStatus.RESET_CONTENT);
    }

    //구독하기
    @GetMapping("/{userId}/subscribe")
    public ResponseEntity subscribeWriter(@PathVariable("userId") Long writerId, @AuthenticationPrincipal UserDto user){
        if(Objects.equals(writerId, user.getUsers().getId())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("구독 실패했습니다.");
        }else{
            try{
                return new ResponseEntity<>(subscribeService.addSubscribe(writerId,user.getUsers().getId()),
                        HttpStatus.CREATED);
            }catch(Exception e){
                log.error("에러 : "+e.getMessage());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("구독 실패했습니다.");
            }
        }
    }
    //구독 취소하기
    @GetMapping("/{userId}/cancelSubscribe")
    public ResponseEntity cancelSubscribeWriter(@PathVariable("userId") Long writerId, @AuthenticationPrincipal UserDto user){
        try{
            return new ResponseEntity<>(subscribeService.cancelSubscribe(writerId,user.getUsers().getId()),
                    HttpStatus.GONE);
        }catch(Exception e){
            log.error("에러 : "+e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("구독 취소 실패했습니다.");
        }
    }
    //구독 글 보기
    @GetMapping("/postSubscribe")
    public ResponseEntity postSubscribe(@AuthenticationPrincipal UserDto user){
        try{
            List<Long> userIds = subscribeService.subscribedWriterList(user.getUsers().getId());
            return ResponseEntity.ok(articleService.findArticlesByUserIds(userIds));
        }catch(Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("글 조회에 실패했습니다.");
        }
    }
}