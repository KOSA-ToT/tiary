package com.example.tiary.article.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tiary.article.dto.request.RequestArticleDto;
import com.example.tiary.article.dto.response.ResponseHashtagDto;
import com.example.tiary.article.entity.Article;
import com.example.tiary.article.entity.ArticleHashtag;
import com.example.tiary.article.entity.Hashtag;
import com.example.tiary.article.repository.ArticleHashtagRepository;
import com.example.tiary.article.repository.HashtagRepository;
import com.example.tiary.article.service.HashtagService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HashtagServiceImpl implements HashtagService {

	private final HashtagRepository hashtagRepository;
	private final ArticleHashtagRepository articleHashtagRepository;

	public HashtagServiceImpl(HashtagRepository hashtagRepository, ArticleHashtagRepository articleHashtagRepository) {
		this.hashtagRepository = hashtagRepository;
		this.articleHashtagRepository = articleHashtagRepository;
	}

	// 해시태그 조회 아티클의 해시태그를 기준으로
	@Transactional(readOnly = true)
	@Override
	public List<ResponseHashtagDto> readHashtag(String categoryName){
		List<ResponseHashtagDto> allResponseHashtagDto = readAllHashtag(categoryName);

		return getRandomHashtag(allResponseHashtagDto,6);
	}
	@Transactional(readOnly = true)
	@Override
	public List<ResponseHashtagDto> readAllHashtag(String categoryName) {
		return articleHashtagRepository
			.findDistinctHashtagsByCategoryName(categoryName)
			.stream()
			.map(ResponseHashtagDto::from)
			.toList();
	}

	private List<ResponseHashtagDto> getRandomHashtag(List<ResponseHashtagDto> responseHashtagDtoList, int count){
		if(responseHashtagDtoList.size() <= count){
			return responseHashtagDtoList;
		}
		List<ResponseHashtagDto> selectHashtag = new ArrayList<>();
		Random random = new Random();

		while(selectHashtag.size() < count){
			int randomIndex = random.nextInt(responseHashtagDtoList.size());
			ResponseHashtagDto responseHashtagDto = responseHashtagDtoList.get(randomIndex);

			if(!selectHashtag.contains(responseHashtagDto)){
				selectHashtag.add(responseHashtagDto);
			}
		}
		return selectHashtag;
	}

	// 해시태그 가공
	@Override
	public List<String> createHashtag(RequestArticleDto requestArticleDto) {
		if (requestArticleDto.getHashtag() == null) {
			return new ArrayList<>();
		}
		Pattern HASHTAG_PATTERN = Pattern.compile("#(\\S+)");
		Matcher matcher = HASHTAG_PATTERN.matcher(requestArticleDto.getHashtag());
		List<String> hashtagList = new ArrayList<>();

		while (matcher.find()) {
			hashtagList.add((matcher.group(1)));
		}
		return hashtagList;
	}

	//해시태그 저장
	@Transactional
	@Override
	public Boolean saveHashtag(RequestArticleDto requestArticleDto, Article article) {
		List<String> hashtagList = createHashtag(requestArticleDto);
		checkHashtagExistAndSave(article, hashtagList);
		return true;
	}

	@Transactional
	@Override
	public Boolean updateHashtag(List<String> hashList, Article article) {
		checkHashtagExistAndSave(article, hashList);
		return true;
	}

	@Transactional
	@Override
	public void removeOldHashtag(Article article) {
		List<ArticleHashtag> articleHashtag = articleHashtagRepository.findAllByArticle(article);
		articleHashtagRepository.deleteAll(articleHashtag);
	}

	//해시태그 체크
	private void checkHashtagExistAndSave(Article article, List<String> hashtagList) {
		for (String tag : hashtagList) {
			Hashtag hashtag = hashtagRepository.findHashtagByHashtagName(tag);
			if (hashtag == null) {
				hashtag = Hashtag.of(tag);
				hashtagRepository.save(hashtag);
			}
			articleHashtagRepository.save(ArticleHashtag.of(article, hashtag));
		}
	}
}
