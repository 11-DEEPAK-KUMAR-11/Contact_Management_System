package com.contactManager.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.contactManager.models.Contact;
import com.contactManager.models.User;
import com.contactManager.repository.ContactRepo;
import com.contactManager.repository.UserRepo;
import com.contactManager.service.ContactService;

import com.razorpay.*;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
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
	public String showDetails(@PathVariable("cId") Integer cId, Model model,Principal principal)
	{
		Optional<Contact> contactOpt=contactRepo.findById(cId);
		
		
		Contact contact=contactOpt.get();
//		if(contact==null)
//		{
//			model.addAttribute("contact",contact);
//			
//			System.out.println("Contact not found");
//			return "User/contactDetails";
//		}
		
		
			
			String userName=principal.getName();
			
			User user=userRepo.findByEmail(userName);
			
			if(user.getId()==contact.getUser().getId())
			{
				model.addAttribute("contact", contact);
				
				model.addAttribute("title", contact.getName());
			}

	      		
			
			System.out.println("contact Id"+cId);
		
		
		
		return "User/contactDetails";
	}
	
	//delete contact handler
	
	@GetMapping("/delete/{cId}")
	public String deleteContact(@PathVariable("cId") Integer cId, Model model)
	{
      
		Optional<Contact> contactOpt=contactRepo.findById(cId);
		Contact contact=contactOpt.get();
		
		//De-linking the user from contact so that cascade all properties will not come into picture and contact will be deleted
		//contact.setUser(null);
		
		// remove the contact from the user's contacts list
	    User user = contact.getUser();
	    user.getContacts().remove(contact);
	    
	    // delete the contact entity
		
		contactRepo.delete(contact);
		
		model.addAttribute("message", "Contact deleted successfully !");
		
		return "redirect:/user/show-contacts/0";
	}
	
	
	//Open update Contact details handler
	@PostMapping("/update-contact/{cId}")
	public String updateContact(@PathVariable("cId") Integer cId,Model model)
	{
		
		model.addAttribute("title","Update - Contact");
		
		Optional<Contact> contactOpt=contactRepo.findById(cId);
		Contact contact=contactOpt.get();
		
		model.addAttribute("contact",contact);
		
		return "User/updateContact";
	}
	
	
	// update contact handler
	
	@PostMapping("/process-update")
	public String updateHandler(@ModelAttribute Contact contact ,@RequestParam("profileImage") MultipartFile file, Principal principal,Model model)
	{
		try{
			
			Contact oldContact=contactRepo.findById(contact.getId()).get();
			
			if(!file.isEmpty())
			{
				//Delete old pic
				File deleteFile=new ClassPathResource("static/image").getFile();
				File file1=new File(deleteFile,oldContact.getImageUrl());
				file1.delete();
				
				
				
				
				//Update new pic
                File saveFile=new ClassPathResource("static/image").getFile();
				
				Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
				
				Files.copy(file.getInputStream(), path,StandardCopyOption.REPLACE_EXISTING);
				
				contact.setImageUrl(file.getOriginalFilename());
				
				System.out.println("Image is uploaded");
				
			}else {
				contact.setImageUrl(oldContact.getImageUrl());
			}
			
			User user=userRepo.findByEmail(principal.getName());
			contact.setUser(user);
			
			contactRepo.save(contact);
			
			model.addAttribute("message", "Contact updated successfully !");
			System.out.println("Data added to database");
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return "redirect:/user/"+contact.getId()+"/contact";
	}
	
	
	//User profile handler
	@GetMapping("/profile")
	public String userProfileHandler(Model model)
	{
		model.addAttribute("title", "Profile page");
		return "User/profile";
	}
	
	
	//Setting Handler
	@GetMapping("/settings")
	public String settingHandler()
	{
		return "User/setting";
	}
	
	
	//Change password handler
	@PostMapping("/change-password")
	public String changePassword(@RequestParam("oldPassword") String oldPassword ,@RequestParam("newPassword") String newPassword, Model model, Principal principal)
	{
		String userName=principal.getName();
		
		User currentUser=userRepo.findByEmail(userName);
		
		if(bCryptPasswordEncoder.matches(oldPassword, currentUser.getPassword()))
		{
			//Change password
			currentUser.setPassword(bCryptPasswordEncoder.encode(newPassword));
			userRepo.save(currentUser);
			
			System.out.println("Password changed successfully..!");
			
			model.addAttribute("message", new String("Password changed successfully..!"));
			
//			return "redirect:/user/index";
			return "User/userDashboard";
		}
		else {
			//error
			System.out.println("Wrong password please enter correct password");
			model.addAttribute("message", new String("WRONG PASSWORD !"));
			
			return "User/setting";
		}
		
		
	}
	
	
	
	//Create order for payment
	@PostMapping("/create_order")
	@ResponseBody
	public String createOrder(@RequestBody Map<String,Object> data) throws Exception
	{
		System.out.println(data);
		double amount=Integer.parseInt(data.get("amount").toString());
		
		var client=new RazorpayClient("rzp_test_O8bLNtDGlCbGoh","l77w16r62y83Pe68jJ3pjung");
		
		JSONObject options = new JSONObject();
		options.put("amount", amount*100);
		options.put("currency", "INR");
		options.put("receipt", "txn_123456");
		
		//Create order
	    Order order=client.orders.create(options);
        
	    System.out.println(order);
		
	    //if you want to store the this data for further use
		return order.toString();
	}
	
	
	
	
	
}
