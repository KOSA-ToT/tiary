package com.example.tiary.myPage.repository;

import com.example.tiary.myPage.dto.response.ResponseMyArticleDto;
import com.example.tiary.myPage.dto.response.ResponseMyCommentDto;
import com.example.tiary.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByNickname(String nickname);

    Optional<Users> findByEmail(String email);

    //내 글 보기
    @Query("SELECT new com.example.tiary.myPage.dto.response.ResponseMyArticleDto(a.title, a.category.id, a.content, a.createdAt, c.categoryName) " +
            "FROM Article a " +
            "JOIN Users u ON a.createdBy = u.nickname " +
            "JOIN Category c ON a.category.id = c.id " +  // 추가: Category 테이블 조인
            "WHERE u.nickname = (SELECT u.nickname FROM Users u WHERE u.id = :userId)")
    List<ResponseMyArticleDto> listMyArticle(@Param("userId") Long userId);

    //내 댓글 보기
    @Query("SELECT new com.example.tiary.myPage.dto.response.ResponseMyCommentDto(c.article.id, c.article.title, c.content, c.createdAt) " +
            "FROM Comment c " +
            "JOIN Users u ON c.createdBy = u.nickname " +
            "JOIN Article a ON c.article.id = a.id " +  // 추가: Article 테이블 조인
            "WHERE u.nickname = (SELECT u.nickname FROM Users u WHERE u.id = :userId)")
    List<ResponseMyCommentDto> listMyComment(@Param("userId") Long userId);
}