package com.example.tiary.article.dto.response;

import com.example.tiary.article.entity.ArticleHashtag;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class ResponseHashtagDto {
	private String hashtagName;

	public static ResponseHashtagDto from(ArticleHashtag articleHashtag){
		return ResponseHashtagDto.builder()
			.hashtagName(articleHashtag.getHashtag().getHashtagName())
			.build();
	}
}
