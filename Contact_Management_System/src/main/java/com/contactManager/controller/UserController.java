package com.contactManager.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.contactManager.models.User;
import com.contactManager.repository.UserRepo;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepo userRepo;
	
	@GetMapping("/index")
	public String dashBoard(Model model, Principal principal)
	{
		//Get the user from username(Email)
		String userName=principal.getName();
		System.out.println("User name "+userName);
		
		User user=userRepo.findByEmail(userName);
		System.out.println("User details "+user);
		
		model.addAttribute("user", user);
		
		return "User/userDashboard";
	}
}
