package com.contactManager.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.contactManager.models.Contact;
import com.contactManager.models.User;
import com.contactManager.repository.UserRepo;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepo userRepo;
	
	//add common data to all handler or all response automatically
	@ModelAttribute
	public void addCommanData(Model model, Principal principal)
	{
		//Get the user from username(Email)
		String userName=principal.getName();
		System.out.println("User name "+userName);
				
		User user=userRepo.findByEmail(userName);
		System.out.println("User details "+user);
				
		model.addAttribute("user", user);
	}
	
	@GetMapping("/index")
	public String dashBoard(Model model, Principal principal)
	{
		//Get the user from username(Email)
//		String userName=principal.getName();
//		System.out.println("User name "+userName);
//		
//		User user=userRepo.findByEmail(userName);
//		System.out.println("User details "+user);
//		
//		model.addAttribute("user", user);
		
		model.addAttribute("title", "User - Dashboard");
		return "User/userDashboard";
	}
	
	//add form(contact) handler
	
	@GetMapping("/add-contact")
	public String addContactForm(Model model)
	{
		model.addAttribute("title", "Add - Contact");
		model.addAttribute("contact", new Contact());
		
		return "User/addContact.html";
	}
	
	
	//Processing add contact form
	
	@PostMapping("/process-contact")
	public String processing(@ModelAttribute Contact contact, Principal principal)
	{
		String userName=principal.getName();
		
		User user=userRepo.findByEmail(userName);
		
		contact.setUser(user);
		
		user.getContacts().add(contact);
		userRepo.save(user);
		
//		System.out.println("Data : "+contact);
		System.out.println("Data added to database");
		return "User/addContact.html";
	}
	
	
	
	
	
	
}
