package com.example.tiary.users.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tiary.users.entity.UserProvider;
import com.example.tiary.users.entity.UserProviderId;

public interface UserProviderRepository extends JpaRepository<UserProvider, UserProviderId> {
	Optional<UserProvider> findByUserProviderId(UserProviderId userProviderId);
}
