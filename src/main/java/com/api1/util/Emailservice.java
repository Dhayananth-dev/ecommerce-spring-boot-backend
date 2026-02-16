package com.api1.util;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
public class Emailservice {
   
	
	private final JavaMailSender javaMailSender;
	@Async
	public void sendOtpEmail(Integer otp,String name,String email) {
		MimeMessage message=javaMailSender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(message);
		try {
			helper.setFrom("dhaya7@gmail.com","EcommerceApplication");
			helper.setTo(email);
			helper.setSubject("Otp for Account Creation - Merchant");
			helper.setText("<h1>Hello " + name + " Your OTP  is " + otp + "</h1>", true);
			javaMailSender.send(message);
		} catch (Exception e) {
			throw new IllegalArgumentException("Failed to Send Email");
		}
	}
	public void otpForgetPassword(Integer otp,String name,String email) {
		MimeMessage message=javaMailSender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(message);
		try {
			helper.setFrom("dhaya7@gmail.com","EcommerceApplication");
			helper.setTo(email);
			helper.setSubject("Otp for Forget Password");
			helper.setText("<h1>Hello " + name + " Your OTP  is " + otp + "</h1>", true);
			javaMailSender.send(message);
		} catch (Exception e) {
			throw new IllegalArgumentException("Failed to Send Email");
		}
	}
}
