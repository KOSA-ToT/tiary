package com.example.tiary.users.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tiary.users.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
	Optional<Users> findByEmail(String email);

	Users findUserByEmail(String email);

	boolean existsByEmail(String email);

	boolean existsByNickname(String nickname);
}
