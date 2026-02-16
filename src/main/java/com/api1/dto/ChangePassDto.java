package com.api1.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangePassDto {
	@NotBlank(message = "Email is Required")
	private String email;
	@NotBlank(message = "Password is Required")
	private String password;
	@NotBlank(message = "confirmPassword is Required")
	private String confirmpassword;

}
