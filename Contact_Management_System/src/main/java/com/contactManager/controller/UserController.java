package com.contactManager.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
	public String processing(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile file, Principal principal)
	{
		try {
			
			String userName=principal.getName();
			
			User user=userRepo.findByEmail(userName);
			
			//Processing and downloading file
			
			if(file !=null && !file.isEmpty())
			{
				//upload the file to folder update the name to contact 
				contact.setImageUrl(file.getOriginalFilename());
				
				//where to store the image
				File saveFile=new ClassPathResource("static/image").getFile();
				
				Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
				
				Files.copy(file.getInputStream(), path,StandardCopyOption.REPLACE_EXISTING);
				
				System.out.println("Image is uploaded");
				
				
				
			}
			
			else {
				//Give some error messages
				System.out.println("File is empty !");
			}
			
			
			
			contact.setUser(user);
			
			user.getContacts().add(contact);
			userRepo.save(user);
			
	        
			System.out.println("Data added to database");
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return "User/addContact.html";
	}
	
	
	
	
	
	
}
