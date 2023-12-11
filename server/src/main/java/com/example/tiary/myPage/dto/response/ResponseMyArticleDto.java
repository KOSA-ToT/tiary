package com.example.tiary.myPage.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class ResponseMyArticleDto {
    private String title;
    private String content;
}
