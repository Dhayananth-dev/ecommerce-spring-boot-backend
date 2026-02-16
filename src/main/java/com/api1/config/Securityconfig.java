package com.api1.config;

import com.api1.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class Securityconfig {

    private final JwtFilter jwtFilter;

    Securityconfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }   
	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config)throws Exception {
		return config.getAuthenticationManager();
	}
	
	@Bean
	SecurityFilterChain chain(HttpSecurity security) {
		return security.csrf(x->x.disable()).authorizeHttpRequests(x -> x.requestMatchers("/auth/**").permitAll().requestMatchers("/customers/products").permitAll().anyRequest().authenticated()).addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class).sessionManagement(x->x.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).build();
	}
}