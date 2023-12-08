package com.example.tiary.myPage.service.impl;

import com.example.tiary.myPage.dto.response.ResponseSubscribeDto;
import com.example.tiary.myPage.entity.Subscribe;
import com.example.tiary.myPage.repository.SubscribeRepository;
import com.example.tiary.myPage.repository.UserRepository;
import com.example.tiary.myPage.service.SubscribeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubscribeServiceImpl implements SubscribeService {
    private final SubscribeRepository subscribeRepository;
    private final UserRepository userRepository;

    public SubscribeServiceImpl(SubscribeRepository subscribeRepository, UserRepository userRepository) {
        this.subscribeRepository = subscribeRepository;
        this.userRepository = userRepository;
    }

    //구독 작가의 구독자 조회
    @Transactional(readOnly = true)
    @Override
    public List<ResponseSubscribeDto> readSubscriber(Long writerId){
//        List<Subscribe> subscribeList = subscribeRepository.findByWriterId(writerId);
        List<ResponseSubscribeDto> subscriberList =  subscribeRepository.showSubscribeList(writerId);
//        return subscribeList.stream().map(ResponseSubscribeDto::from).toList();
        return subscriberList;
    }
    //구독자의 구독 작가 조회
    @Transactional(readOnly = true)
    @Override
    public List<ResponseSubscribeDto> readsubscribedWriter(Long userId){
//        List<Subscribe> subscribeList = subscribeRepository.findByUserId(userId);
//        return subscribeList.stream().map(ResponseSubscribeDto::from).toList();
        List<ResponseSubscribeDto> subscribedWriterList =  subscribeRepository.showSubscribedWriterList(userId);
        return subscribedWriterList;
    }
}