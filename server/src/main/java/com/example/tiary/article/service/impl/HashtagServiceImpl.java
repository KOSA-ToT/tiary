package com.example.tiary.article.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tiary.article.dto.request.RequestArticleDto;
import com.example.tiary.article.entity.Article;
import com.example.tiary.article.entity.ArticleHashtag;
import com.example.tiary.article.entity.Hashtag;
import com.example.tiary.article.repository.ArticleHashtagRepository;
import com.example.tiary.article.repository.HashtagRepository;
import com.example.tiary.article.service.HashtagService;
@Service
public class HashtagServiceImpl implements HashtagService {

	private final HashtagRepository hashtagRepository;
	private final ArticleHashtagRepository articleHashtagRepository;

	public HashtagServiceImpl(HashtagRepository hashtagRepository, ArticleHashtagRepository articleHashtagRepository) {
		this.hashtagRepository = hashtagRepository;
		this.articleHashtagRepository = articleHashtagRepository;
	}

	// 해시태그 가공
	@Override
	public List<String> createHashtag(RequestArticleDto requestArticleDto) {

		Pattern HASHTAG_PATTERN = Pattern.compile("#(\\S+)");
		Matcher matcher = HASHTAG_PATTERN.matcher(requestArticleDto.getHashtag());
		List<String> hashtagList = new ArrayList<>();

		while(matcher.find()){
			hashtagList.add((matcher.group(1)));
		}
		return hashtagList;
	}

	//해시태그 저장
	@Transactional
	@Override
	public Boolean saveHashtag(List<String> hashtagList, Article article){
		for(String tag : hashtagList){
			Hashtag hashtag = hashtagRepository.findHashtagByHashtagName(tag);
			if(hashtag == null){
				hashtag = Hashtag.of(tag);
				hashtagRepository.saveAndFlush(hashtag);
			}
			articleHashtagRepository.saveAndFlush(ArticleHashtag.of(article, hashtag));
		}
		return true;
	}
}
