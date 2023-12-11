package com.example.tiary.myPage.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class ResponseMyCommentDto {
    private Long articleId;
    private String content;
}
