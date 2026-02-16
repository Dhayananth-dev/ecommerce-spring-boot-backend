package com.api1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api1.entity.CustomerUser;
import com.api1.entity.User;

public interface CustomerUserRepo extends JpaRepository<com.api1.entity.CustomerUser, Long> {
	Optional<CustomerUser> findByUser(User user);
}
