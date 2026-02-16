package com.api1.util;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.api1.dto.Merchantdto;
import com.api1.dto.UserDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisService {
   
	private final RedisTemplate<String,Object> redisTemplate;
	@Async
	public void saveOtp(Integer otp,String email) {
		redisTemplate.opsForValue().set(email+"_otp", otp,Duration.ofMinutes(5));
	}
	@Async
	public void saveTempData(Merchantdto merchantDto, String email) {
		redisTemplate.opsForValue().set(email+"_merchant", merchantDto,Duration.ofMinutes(30));
	}
	public Integer getOtp(String email) {
		return (Integer) redisTemplate.opsForValue().get(email+"_otp");
	}
	
	public Merchantdto getTempDataMerchant(String email) {
		return (Merchantdto) redisTemplate.opsForValue().get(email+"_merchant");
	}
	@Async
	public void saveTempData(UserDto userdto, String email) {
        redisTemplate.opsForValue().set(email+"_user",userdto,Duration.ofMinutes(30));
		
	}
	public UserDto getTempDatauser(String email) {
		return (UserDto) redisTemplate.opsForValue().get(email+"_user");

}
}