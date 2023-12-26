package com.example.tiary.article.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.tiary.article.dto.request.RequestArticleDto;
import com.example.tiary.article.service.ArticleLikesService;
import com.example.tiary.article.service.ArticleService;
import com.example.tiary.users.dto.UserDto;
import com.example.tiary.users.entity.Users;

@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleLikesService articleLikesService;

    public ArticleController(ArticleService articleService, ArticleLikesService articleLikesService) {
        this.articleService = articleService;
        this.articleLikesService = articleLikesService;
    }

    //게시물 리스트 조회
    @GetMapping
    public ResponseEntity getArticleList(@RequestParam(value = "hashtag", required = false) String hashtag) {

        if (hashtag != null) {
            return new ResponseEntity(articleService.readArticleFromHashtag(hashtag), HttpStatus.OK);
        }
        return new ResponseEntity<>(articleService.readRandomList(), HttpStatus.OK);
    }

    //게시물 단건 조회
    @GetMapping("/{article-id}")
    public ResponseEntity getArticle(@PathVariable("article-id") Long id) {
        return new ResponseEntity<>(articleService.readArticle(id), HttpStatus.OK);
    }

    //게시물 등록
    @PostMapping
    public ResponseEntity postArticle(
            @RequestBody RequestArticleDto requestArticleDto,
            @AuthenticationPrincipal UserDto user) throws
            IOException {

        return new ResponseEntity<>(
                articleService.createArticle(user.getUsers().getId(), requestArticleDto, requestArticleDto.getStoreName()),
                HttpStatus.CREATED);
    }

    //게시물 수정
    @PatchMapping("/{article-id}")
    public ResponseEntity patchArticle(@PathVariable("article-id") Long articleId,
                                       @RequestBody RequestArticleDto requestArticleDto,
                                       @AuthenticationPrincipal UserDto user) throws IOException {
        return new ResponseEntity<>(
                articleService.updateArticle(user.getUsers().getId(), articleId, requestArticleDto, requestArticleDto.getStoreName()),
                HttpStatus.RESET_CONTENT);
    }

    //게시물 삭제
    @DeleteMapping("/{article-id}")
    public ResponseEntity deleteArticle(@PathVariable("article-id") Long articleId,
                                        @AuthenticationPrincipal UserDto users) {

        return new ResponseEntity<>(articleService.deleteArticle(articleId, users.getUsers().getId()), HttpStatus.RESET_CONTENT);
    }

    @GetMapping("/{article-id}/likes")
    public ResponseEntity pushLikesArticle(@PathVariable("article-id") Long articleId,
                                           @AuthenticationPrincipal UserDto users,
                                           @RequestParam("check") String check) {

        if (check.equals("likes")) {
            articleLikesService.choiceLikes(articleId, users.getUsers().getId());
        } else {
            articleLikesService.cancleLikes(articleId, users.getUsers().getId());
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}