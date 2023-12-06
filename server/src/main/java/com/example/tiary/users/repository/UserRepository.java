package com.example.tiary.users.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tiary.users.entity.Users;
public interface UserRepository extends JpaRepository<Users, Long> {
	Optional<Users> findByEmail(String email);

	Users findUserByEmail(String email);
}
