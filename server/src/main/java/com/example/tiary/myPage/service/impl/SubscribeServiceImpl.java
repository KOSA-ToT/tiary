package com.example.tiary.myPage.service.impl;

import com.example.tiary.article.entity.Article;
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
    //구독 작가 리스트 만들기
    @Transactional(readOnly = true)
    @Override
    public List<Long> subscribedWriterList(Long userId){
        List<Long> subscribedWriterCount =  subscribeRepository.showSubscribedWriterCount(userId);
        return subscribedWriterCount;
    }
    //구독하기
    @Transactional
    @Override
    public boolean addSubscribe(Long writerId, Long userId){
        if(subscribeRepository.findByUserIdAndWriterId(userId,writerId).isPresent()){
            return false;
        }else{
            try {
                Subscribe subscribe = new Subscribe(userId, writerId);
                subscribeRepository.save(subscribe);
                return true; // 저장 성공 시 true 반환
            } catch (Exception e) {
                // 예외 발생 시 처리
                return false;
            }
        }

    }
    //구독 취소하기
    @Transactional
    @Override
    public boolean cancelSubscribe(Long writerId, Long userId){
        try {
            Subscribe subscribe = new Subscribe(userId, writerId);
            subscribeRepository.delete(subscribe);
            return true; // 삭제 성공 시 true 반환
        } catch (Exception e) {
            // 예외 발생 시 처리
            return false;
        }
    }
}