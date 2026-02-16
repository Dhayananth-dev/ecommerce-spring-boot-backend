package com.api1.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api1.dto.ChangePassDto;
import com.api1.dto.ForgetPasswordDto;
import com.api1.dto.Logindto;
import com.api1.dto.Merchantdto;
import com.api1.dto.Otpdto;
import com.api1.dto.Passworddto;
import com.api1.dto.UserDto;
import com.api1.services.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
	private final AuthService authservice;

	@PostMapping("/login")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> login(@Valid @RequestBody Logindto login) {
		return authservice.login(login.getEmail(), login.getPassword());
	}

	@GetMapping("/me")
	@PreAuthorize("hasAnyRole('ADMIN','USER','MERCHANT')")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> logedUsers(Principal principal) {
		return authservice.viewUser(principal.getName());
	}

	@PatchMapping("/password")
	@PreAuthorize("hasAnyRole('ADMIN','USER','MERCHANT')")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> updatePassword(Principal principal, @RequestBody Passworddto passworddto) {
		return authservice.updatePassword(principal.getName(), passworddto.getOldPassword(),
				passworddto.getNewPassword());
	}

	@PostMapping("/merchant/register")
	@ResponseStatus(HttpStatus.CREATED)
	public Map<String, Object> createMerchantaccount(@Valid @RequestBody Merchantdto merchantdto) {
		return authservice.registerMerchant(merchantdto);
	}

	@PatchMapping("/merchant/otp")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> verifyOtp(@Valid @RequestBody Otpdto dto) {
		return authservice.verifyMerchantOtp(dto);
	}

	@PatchMapping("/merchant/resend/{email}")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> resendOtp(@PathVariable String email) {
		return authservice.resendOtp(email);
	}

	@PostMapping("/user/register")
	@ResponseStatus(HttpStatus.CREATED)
	public Map<String, Object> createUser(@Valid @RequestBody UserDto userdto) {
		return authservice.registerUser(userdto);
	}

	@PatchMapping("/user/otp")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> verifyOtpuser(@Valid @RequestBody Otpdto dto) {
		return authservice.verifyUserOtp(dto);
	}

	@PatchMapping("/user/resend/{email}")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> resendOtpuser(@PathVariable String email) {
		return authservice.resendOtp(email);
	}

	@PatchMapping("/forgetpassword")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> forgetPassword(@Valid @RequestBody ForgetPasswordDto passworddto) {
		return authservice.forgetPassword(passworddto.getEmail());
	}

	@PatchMapping("/forgetpassword/otp")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> verifyForgetpasswordotp(@Valid @RequestBody Otpdto dto) {
		return authservice.verifyForgetpasswordOtp(dto);
	}

	@PatchMapping("/forgetpassword/changepassword")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> changePassword(@Valid @RequestBody ChangePassDto passworddto) {
		return authservice.changePass(passworddto);
	}
}
