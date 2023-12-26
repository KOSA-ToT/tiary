package com.example.tiary.myPage.repository;

import com.example.tiary.myPage.dto.response.ResponseSubscribeDto;
import com.example.tiary.myPage.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
    List<Subscribe> findByWriterId(@Param("writerId") Long writerId);

    List<Subscribe> findByUserId(@Param("userId") Long userId);

    //구독자 조회
    @Query("SELECT new com.example.tiary.myPage.dto.response.ResponseSubscribeDto(s.userId, s.writerId, u.nickname, u.userPicture) FROM Subscribe s LEFT JOIN Users u ON s.userId = u.id WHERE s.writerId = :writerId")
    List<ResponseSubscribeDto> showSubscribeList(@Param("writerId") Long writerId);

    //구독 작가 조회
    @Query("SELECT new com.example.tiary.myPage.dto.response.ResponseSubscribeDto(s.userId, s.writerId, u.nickname, u.userPicture) FROM Subscribe s LEFT JOIN Users u ON s.writerId = u.id WHERE s.userId = :userId")
    List<ResponseSubscribeDto> showSubscribedWriterList(@Param("userId") Long userId);

    Optional<Subscribe> findByUserIdAndWriterId(@Param("userId") Long userId, @Param("writerId") Long writerId);

    //구독 작가 리스트 만들기
    @Query("SELECT s.writerId FROM Subscribe s LEFT JOIN Users u ON s.writerId = u.id WHERE s.userId = :userId")
    List<Long> showSubscribedWriterCount(@Param("userId") Long userId);
}
