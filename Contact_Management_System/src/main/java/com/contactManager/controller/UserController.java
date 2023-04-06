package com.contactManager.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.contactManager.models.Contact;
import com.contactManager.models.User;
import com.contactManager.repository.ContactRepo;
import com.contactManager.repository.UserRepo;
import com.contactManager.service.ContactService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private ContactRepo contactRepo;
	
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
	public String processing(@ModelAttribute Contact contact,BindingResult result,@RequestParam("profileImage") MultipartFile file, Principal principal,Model model)
	{
		try {
			
			String userName=principal.getName();
			
			User user=userRepo.findByEmail(userName);
			
			if (contactService.isUserAlreadyPresent(contact.getEmail())) {
	        	
	            FieldError error = new FieldError("contact", "email", "Email already exists");
	            System.out.println("This email id is already exist! ");
	            
	            
	            result.addError(error);
	        }

	        if (result.hasErrors()) {
	        	
	        	System.out.println("Error"+result.toString());
	        	model.addAttribute("contact", contact);
	            return "User/addContact";
	        }
			
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
				contact.setImageUrl("contact.png");
			}
			
			
			
			contact.setUser(user);
			
			user.getContacts().add(contact);
			userRepo.save(user);
			
			model.addAttribute("message", "Contact added successfully !");
			System.out.println("Data added to database");
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return "User/addContact.html";
	}
	
	
	
	//show all contacts handler
	@GetMapping("/show-contacts/{page}")
	public String showContacts(@PathVariable("page") Integer page ,Model model,Principal principal)
	{
		String userName=principal.getName();
				
		User user=userRepo.findByEmail(userName);
		
		Pageable pageable=PageRequest.of(page, 5);
		
		Page<Contact> contacts=contactRepo.findContactByUser(user.getId(), pageable);
		model.addAttribute("contacts",contacts);
		model.addAttribute("currentPage",page);
		model.addAttribute("totalPages",contacts.getTotalPages());
		
		model.addAttribute("title", "User Contacts");
		return "User/showContact";
	}
	
	//showing particular contact details
	
	@GetMapping("/{cId}/contact")
	public String showDetails(@PathVariable("cId") Integer cId, Model model)
	{
		Contact contact=contactRepo.findById(cId).get();
		model.addAttribute("contact", contact);
		
		System.out.println("contact Id"+cId);
		return "User/contactDetails";
	}
	
	
	
	
}
