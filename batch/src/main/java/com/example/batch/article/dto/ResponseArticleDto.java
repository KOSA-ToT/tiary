package com.example.batch.article.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.batch.article.entity.Article;
import com.example.batch.article.entity.RelatedArticle;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class ResponseArticleDto {
	private Long originArticleId;
	private List<ResponseRelatedArticleDto> responseRelatedArticleDtoList;


	public static ResponseArticleDto of(Long originArticleId, List<ResponseRelatedArticleDto> responseRelatedArticleDtoList) {
		return ResponseArticleDto.builder()
			.originArticleId(originArticleId)
			.responseRelatedArticleDtoList(responseRelatedArticleDtoList)
			.build();
	}

	public static ResponseArticleDto of(Article originArticle, List<RelatedArticle> relatedArticles) {
		List<ResponseRelatedArticleDto> relatedArticleDtoList = relatedArticles.stream()
			.map(ResponseRelatedArticleDto::of)
			.collect(Collectors.toList());

		return ResponseArticleDto.builder()
			.originArticleId(originArticle.getId())
			.responseRelatedArticleDtoList(relatedArticleDtoList)
			.build();
	}

}
