package com.contactManager.controller;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ForgotPasswordController {

	
	Random random=new Random(1000);
	
	//E-mail id form with OTP send open handler
	@GetMapping("/forgot")
	public String openForgotFom()
	{
		return "forgotEmailForm";
	}
	
	@PostMapping("/send-otp")
	public String sendOTP(@RequestParam("email") String email)
	{
		System.out.println("Email "+email);
		
		//Generating 6 digit otp
		int otp=random.nextInt(999999);
		
		System.out.println("OTP "+otp);
		
		//sending otp to above email
		
		return "verifyOTP";
	}
	
	
	
	
	
	
	
}
