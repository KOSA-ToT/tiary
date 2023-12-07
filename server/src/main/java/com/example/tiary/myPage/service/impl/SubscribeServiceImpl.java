package com.example.tiary.myPage.service.impl;

import com.example.tiary.article.dto.response.ResponseArticleDto;
import com.example.tiary.myPage.dto.response.ResponseSubscribeDto;
import com.example.tiary.myPage.entity.Subscribe;
import com.example.tiary.myPage.repository.SubscribeRepository;
import com.example.tiary.myPage.service.SubscribeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubscribeServiceImpl implements SubscribeService {
    private final SubscribeRepository subscribeRepository;

    public SubscribeServiceImpl(SubscribeRepository subscribeRepository) {
        this.subscribeRepository = subscribeRepository;
    }

    //구독 작가의 구독자 조회
    @Transactional(readOnly = true)
    @Override
    public List<ResponseSubscribeDto> readSubscriber(Long writerId){
        List<Subscribe> subscribeList = subscribeRepository.findByWriterId(writerId);
        return subscribeList.stream().map(ResponseSubscribeDto::from).toList();
    }
    //구독자의 구독 작가 조회
    @Transactional(readOnly = true)
    @Override
    public List<ResponseSubscribeDto> readsubscribedWriter(Long userId){
        List<Subscribe> subscribeList = subscribeRepository.findByUserId(userId);
        return subscribeList.stream().map(ResponseSubscribeDto::from).toList();
    }
}