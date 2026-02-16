package com.api1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api1.entity.Merchant;
import com.api1.entity.User;

public interface Merchantrepo extends JpaRepository<Merchant,Long>{

	Optional<Merchant> findByUser(User user);

}
