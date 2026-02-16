package com.api1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api1.entity.User;

public interface Userrepo extends JpaRepository<User,Integer> {

	boolean existsByEmail(String adminEmail);

	Optional<User> findByEmail(String email);

	boolean existsByEmailOrMobile(String email, Long mobile);

}
