package com.example.tiary.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tiary.article.entity.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
	Hashtag findHashtagByHashtagName(String hashtagName);
}