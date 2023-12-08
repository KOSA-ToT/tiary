package com.example.tiary.myPage.service;

import com.example.tiary.myPage.dto.response.ResponseSubscribeDto;

import java.util.List;

public interface SubscribeService {
    public List<ResponseSubscribeDto> readSubscriber(Long writerId);

    public List<ResponseSubscribeDto> readsubscribedWriter(Long userId);
}
