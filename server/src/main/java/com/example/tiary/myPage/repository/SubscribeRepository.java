package com.example.tiary.myPage.repository;

import com.example.tiary.myPage.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
    List<Subscribe> findByWriterId(Long writerId);

    List<Subscribe> findByUserId(Long userId);
}
