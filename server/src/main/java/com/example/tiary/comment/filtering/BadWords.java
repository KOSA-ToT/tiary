package com.example.tiary.comment.filtering;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Getter
@Component
public class BadWords {
    private List<String> badWords = Arrays.asList("돌아이", "미쳤냐");

    public String filterBadWords(String content) {
        for (String badWord : badWords) {
            content = content.replaceAll("(?i)" + badWord, "*".repeat(badWord.length()));
        }
        return content;
    }
}
