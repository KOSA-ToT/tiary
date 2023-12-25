package com.example.tiary.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tiary.users.entity.Users;

public interface AdminRepository extends JpaRepository<Users, Long> {
}
