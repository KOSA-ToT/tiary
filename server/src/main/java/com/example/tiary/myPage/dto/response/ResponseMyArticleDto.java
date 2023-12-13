package com.example.tiary.myPage.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class ResponseMyArticleDto {
    private String title;
    private Long categoryId;
    private String content;
    private LocalDateTime createdAt;
    private String categoryName;

}
