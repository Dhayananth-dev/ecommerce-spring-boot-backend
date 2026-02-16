package com.api1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class UserDto {
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;
	@NotEmpty(message = "Name is Required")
    private String name;
	@NotEmpty(message = "Email is Required")
    private String email;
	@NotEmpty(message = "Name is Required")
    private String password;
	@NotNull(message = "Mobile Number is Required")
    private Long  mobile ;
	@NotNull(message = "Gender is Required")
	private String gender;
	@JsonProperty(access = Access.READ_ONLY)
	private String status;
    
}
