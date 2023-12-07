package com.example.tiary.myPage.repository;

import com.example.tiary.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByNickname(String nickname);

    Optional<Users> findByEmail(String email);
}