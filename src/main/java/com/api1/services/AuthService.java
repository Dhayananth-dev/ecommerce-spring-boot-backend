package com.api1.services;
import java.util.Map;

import com.api1.dto.ChangePassDto;
import com.api1.dto.Merchantdto;
import com.api1.dto.Otpdto;
import com.api1.dto.UserDto;

import jakarta.validation.Valid;
public interface AuthService {
	Map<String, Object> login(String username, String password);

	Map<String, Object> viewUser(String email);

	Map<String, Object> updatePassword(String name, String oldPassword, String newPassword);

	Map<String, Object> registerMerchant(Merchantdto merchantdto);

	Map<String, Object> resendOtp(String email);

	Map<String, Object> verifyMerchantOtp(Otpdto dto);

	Map<String, Object> registerUser(UserDto userdto);
	
	public Map<String, Object> verifyUserOtp(Otpdto dto);

	Map<String, Object> forgetPassword(String email);

	Map<String, Object> verifyForgetpasswordOtp( Otpdto dto);

	Map<String, Object> changePass(ChangePassDto passworddto);

}
