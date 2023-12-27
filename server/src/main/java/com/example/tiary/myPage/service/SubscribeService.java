package com.example.tiary.myPage.service;

import com.example.tiary.myPage.dto.response.ResponseSubscribeDto;

import java.util.List;

public interface SubscribeService {
    //구독자 조회하기
    public List<ResponseSubscribeDto> readSubscriber(Long writerId);
    //구독 작가 조회하기
    public List<ResponseSubscribeDto> readsubscribedWriter(Long userId);
    //구독 추가하기
    boolean addSubscribe(Long writerId,Long userId);
    //구독 삭제하기
    boolean cancelSubscribe(Long writerId,Long userId);
    //구독 작가 리스트
    List<Long> subscribedWriterList(Long userId);
}
