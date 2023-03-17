package com.contactManager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

	@GetMapping("/home")
	public String home(Model model)
	{
		model.addAttribute("title", "Home - Contact Manager");
		return "home";
	}
	
	@GetMapping("/about")
	public String about(Model model)
	{
		model.addAttribute("title", "About - Contact Manager");
		return "about";
	}
	
	
	@GetMapping("/signup")
	public String signup(Model model)
	{
		model.addAttribute("title", "Signup - Contact Manager");
		return "signup";
	}
	
	
	
	
	
}
