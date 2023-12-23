package com.example.tiary.myPage.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class ResponseMyCommentDto {
    private Long id;
    private Long articleId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
}
