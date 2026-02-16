package com.api1.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.api1.entity.User;
import com.api1.repository.Userrepo;

import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
public class Customuser implements UserDetailsService {

	
private final Userrepo repo;

@Override
public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	
     User user = repo.findByEmail(email).orElseThrow(() -> new BadCredentialsException("Invalid Email"));
	return new Customuserdetails(user);	
}


}
