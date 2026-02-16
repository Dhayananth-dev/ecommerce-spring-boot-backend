package com.api1.services;

import java.security.SecureRandom;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api1.dao.UserDao;
import com.api1.dto.ChangePassDto;
import com.api1.dto.Merchantdto;
import com.api1.dto.Otpdto;
import com.api1.dto.UserDto;
import com.api1.entity.CustomerUser;
import com.api1.entity.Merchant;
import com.api1.entity.User;
import com.api1.enums.UserRole;
import com.api1.mapper.UserMapper;
import com.api1.security.JwtService;
import com.api1.util.Emailservice;
import com.api1.util.RedisService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthserviceImlime  implements AuthService{
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;
  private final UserDao userdao;
  private final PasswordEncoder passwordencoder;
  private final Emailservice emailservice;
  private final RedisService redisService;
  private final UserMapper userMapper;
  @Override
  public Map<String, Object> login(String email,String password) {
	  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
	  UserDetails customuserdetails=userDetailsService.loadUserByUsername(email);
	  String token=jwtService.generateToken(customuserdetails);
	  return Map.of("message","Login Success","Token",token);
  }
  @Override
  public Map<String, Object> viewUser(String email) {
	User user=userdao.findByEmail(email);
	return Map.of("message", "Data Found", "user", userMapper.toUserDto(user));
  }
  @Override
  public Map<String, Object> updatePassword(String name, String oldPassword, String newPassword) {
	User user=userdao.findByEmail(name);
	if (passwordencoder.matches(oldPassword,user.getPassword())) {
		user.setPassword(passwordencoder.encode(newPassword));
		userdao.save(user);
		return Map.of("message", "Password Updated Success", "user", userMapper.toUserDto(user));
	}
	throw new IllegalArgumentException("Old Password Is Not Matching");
	
  }
  @Override
  public Map<String, Object> registerMerchant(Merchantdto merchantdto) {
	  if (userdao.checkEmailAndMobileDuplicate(merchantdto.getEmail(), merchantdto.getMobile()))
			throw new IllegalArgumentException("Already Account Exists with Email or Mobile");
	  
	  Merchantdto tempData = redisService.getTempDataMerchant(merchantdto.getEmail());
		if (tempData != null)
			throw new IllegalArgumentException("Already Otp Sent First Verify It or After 30 minutes Try Again");
		Integer otp = generateOtp();
		emailservice.sendOtpEmail(otp, merchantdto.getName(), merchantdto.getEmail());
		redisService.saveOtp(otp, merchantdto.getEmail());
		redisService.saveTempData(merchantdto, merchantdto.getEmail());
		return Map.of("message","Otp Sent Succes Verify within 5 minutes");
	}

	private Integer generateOtp() {
		return new SecureRandom().nextInt(100000, 1000000);
	}
	@Override
	public Map<String, Object> resendOtp(String email) {
		Merchantdto merchantDto = redisService.getTempDataMerchant(email);
		if (merchantDto == null)
			throw new IllegalArgumentException("No Account Exists recreate account");
		int otp = generateOtp();
		emailservice.sendOtpEmail(otp, merchantDto.getName(), merchantDto.getEmail());
		redisService.saveOtp(otp, merchantDto.getEmail());
		return Map.of("message", "OTP Resent Success");
	}
	@Override
	public Map<String, Object> verifyMerchantOtp(Otpdto dto) {
		Integer storedOtp = redisService.getOtp(dto.getEmail());
		Merchantdto merchantDto = redisService.getTempDataMerchant(dto.getEmail());
		if (merchantDto == null)
			throw new IllegalArgumentException("No Account Exists recreate account");
		if (storedOtp == null)
			throw new IllegalArgumentException("Otp Expired, Try Resending");
		if (storedOtp.equals(dto.getOtp())) {
			if (userdao.checkEmailAndMobileDuplicate(merchantDto.getEmail(), merchantDto.getMobile()))
			          throw new IllegalArgumentException("Already Account Exists with Email or Mobile");
			User user=userMapper.toUserEntity(merchantDto);
			userdao.save(user);
			Merchant merchant =userMapper.toMerchantEntity(merchantDto,user);
			userdao.save(merchant);
			return Map.of("message", "Account Created Success", "user", userMapper.toMerchantDto(merchant));
		} else {
			throw new IllegalArgumentException("Otp Missmatch Try Again");
		}
	}
	@Override
	public Map<String, Object> registerUser(UserDto userdto) {
		if (userdao.checkEmailAndMobileDuplicate(userdto.getEmail(), userdto.getMobile()))
			throw new IllegalArgumentException("Already Account Exists with Email or Mobile");
	  
	  UserDto tempData = redisService.getTempDatauser(userdto.getEmail());
		if (tempData != null)
			throw new IllegalArgumentException("Already Otp Sent First Verify It or After 30 minutes Try Again");
		Integer otp = generateOtp();
		emailservice.sendOtpEmail(otp,userdto.getName(), userdto.getEmail());
		redisService.saveOtp(otp, userdto.getEmail());
		redisService.saveTempData(userdto, userdto.getEmail());
		return Map.of("message","Otp Sent Succes Verify within 5 minutes");
	}
	@Override
	public Map<String, Object> verifyUserOtp(Otpdto dto) {
		Integer storedOtp = redisService.getOtp(dto.getEmail());
	    UserDto userDto = redisService.getTempDatauser(dto.getEmail());
		if (userDto == null)
			throw new IllegalArgumentException("No Account Exists recreate account");
		if (storedOtp == null)
			throw new IllegalArgumentException("Otp Expired, Try Resending");
		if (storedOtp.equals(dto.getOtp())) {
			User user = userMapper.toCustomeruserEntity(userDto);
			userdao.save(user);
			CustomerUser customeruser =userMapper.toCustomerEntity(userDto, user);
			userdao.save(customeruser);
			return Map.of("message", "Account Created Success", "user", userMapper.toCustomerDto(customeruser));
		} else {
			throw new IllegalArgumentException("Otp Missmatch Try Again");
		}
	}
	@Override
	public Map<String, Object> forgetPassword(String email) {
		   User user=userdao.findByEmail(email);
		   if (user.isIsactive()) {
			   Integer otp = generateOtp();
				emailservice.otpForgetPassword(otp, user.getUsername(), user.getEmail());
				redisService.saveOtp(otp, user.getEmail());
				return Map.of("message","Otp Sent Succes Verify within 5 minutes");
		}else {
		throw new NoSuchElementException("Email is not found");	
		}
		}
	@Override
	public Map<String, Object> verifyForgetpasswordOtp(Otpdto dto) {
		Integer storedOtp = redisService.getOtp(dto.getEmail());
		if (dto.getOtp().equals(storedOtp)) {
			  UserDetails customuserdetails=userDetailsService.loadUserByUsername(dto.getEmail());
			  String token=jwtService.generateToken(customuserdetails);
			  return Map.of("message","Otp is Correct","Token",token);
		}else {
			throw new IllegalArgumentException("Otp is Mismatch");	
		}
	}
	@Override
	public Map<String, Object> changePass(ChangePassDto passworddto) {
		if (!passworddto.getPassword().equals(passworddto.getConfirmpassword())) {
			throw new RuntimeException("Password Is Not match for confirm Password");
		}
		User user=userdao.findByEmail(passworddto.getEmail());
		user.setPassword(passwordencoder.encode(passworddto.getPassword()));
	     userdao.save(user);
		return Map.of("Message","Password Change SuccessFully");
	}		 
}
	
  
