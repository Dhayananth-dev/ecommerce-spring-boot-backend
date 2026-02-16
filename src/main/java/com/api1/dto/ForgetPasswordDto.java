package com.api1.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ForgetPasswordDto {
    @NotEmpty(message = "Email is Required")
    private String email;
}
