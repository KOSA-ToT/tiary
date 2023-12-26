package com.example.tiary.myPage.repository;

import com.example.tiary.myPage.dto.response.ResponseMyArticleDto;
import com.example.tiary.myPage.dto.response.ResponseMyCommentDto;
import com.example.tiary.users.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByNickname(String nickname);

    Users findByEmail(String email);

    //내 글 보기
    @Query("SELECT new com.example.tiary.myPage.dto.response.ResponseMyArticleDto(a.id, a.title, a.category.id, a.content, a.createdAt, c.categoryName, ai.imgUrl) " +
            "FROM Article a " +
            "JOIN Users u ON a.users.id = u.id " +
            "JOIN Category c ON a.category.id = c.id " +
            "LEFT JOIN ArticleImage ai ON a.id = ai.article.id " +  // LEFT JOIN 사용
            "WHERE u.id = :userId")
    Page<ResponseMyArticleDto> listMyArticle(@Param("userId") Long userId, Pageable pageable);

    //내 댓글 보기
    @Query("SELECT new com.example.tiary.myPage.dto.response.ResponseMyCommentDto(c.id, c.article.id, c.article.title, c.content, c.createdAt) " +
            "FROM Comment c " +
            "JOIN Users u ON c.users.id = u.id " +
            "JOIN Article a ON c.article.id = a.id " +  // 추가: Article 테이블 조인
            "WHERE u.id = :userId")
    Page<ResponseMyCommentDto> listMyComment(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT COUNT(a) FROM Article a WHERE a.users.id = :userId")
    Integer countArticlesById(@Param("userId") Long userId);
}