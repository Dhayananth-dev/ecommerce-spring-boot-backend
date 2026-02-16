package com.api1.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.api1.entity.User;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class Customuserdetails implements UserDetails {
	
	private final User user;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> l=Arrays.asList(new SimpleGrantedAuthority("ROLE_"+user.getRole().name()));
		return l;
	}

	@Override
	public @Nullable String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}
	
	@Override
	public boolean isEnabled() {
		return user.isIsactive();
	}
	 
}
