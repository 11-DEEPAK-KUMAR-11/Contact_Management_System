package com.contactManager.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



import com.contactManager.models.User;

import com.contactManager.service.UserService;


import jakarta.validation.Valid;






@Controller
public class HomeController {

	@Autowired
    private UserService userService;
	
	
	
	
	
	
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
		model.addAttribute("user", new User());
		
		return "signup";
	}
	
	//Handler for registering user
	
	@PostMapping("/do_register")
    public String addUser(@Valid @ModelAttribute("user") User user, BindingResult result, @RequestParam(value="agreement" , defaultValue="false") boolean agreement,  Model model) {

		
	
		if(!agreement)
			{
				//System.out.println("You have not agreed terms and conditions");
				//result.rejectValue("agreement", null, "Please accept the terms and conditions!");
			    FieldError error = new FieldError("user", "enabled", "You have not accepted terms and conditions !");
                System.out.println("Please accept terms and conditions! ");
            
            
                result.addError(error);
			}
		
        if (userService.isUserAlreadyPresent(user.getEmail())) {
        	
            FieldError error = new FieldError("user", "email", "Email already exists");
            System.out.println("This email id is already exist! ");
            
            
            result.addError(error);
        }

        if (result.hasErrors()) {
        	
        	System.out.println("Error"+result.toString());
        	model.addAttribute("user", user);
            return "signup";
        }

        userService.saveUser(user);
        model.addAttribute("message", "You are registered successfully ! Please login..!");
        return "signup";
    }

	
	
//	@PostMapping("/do_register")
//	public String registerUser(@ModelAttribute("user") User user, @RequestParam(value="agreement" , defaultValue="false") boolean agreement, HttpSession session,
//            Model model,
//            HttpServletRequest request,
//            HttpServletResponse response,
//            ServletContext servletContext )
//	{
//		 model.addAttribute("session", session);
//		
//		try {
//			
//			if(!agreement)
//			{
//				System.out.println("You have not agreed terms and conditions");
//				throw new Exception("You have not agreed terms and conditions");
//			}
//			
//			System.out.println("Agreement :"+agreement);
//			System.out.println("User : "+user);
//			
//			user.setRole("Role_USER");
//			user.setEnabled(true);
//			user.setImageUrl("default.png");
//			
//			User result=userRepo.save(user);
//			
//			model.addAttribute("user", new User());
//			session.setAttribute("message", new Message("Successfully registered !", "alert-success"));
//            //session.setAttribute("message", new Message("Successfully registered !", "alert-success")); 
//			
//			// Add request, session, and servletContext objects as context variables
//	        model.addAttribute("request", request);
//            //model.addAttribute("session", session);
//	        model.addAttribute("servletContext", servletContext);
//			
//	        return "signup";
//			
//		} catch (Exception e) {
//
//           e.printStackTrace();
//           model.addAttribute("user", user);
//           session.setAttribute("message", new Message("Something went wrong"+e.getMessage(), "alert-danger"));
//           
//        // Add request, session, and servletContext objects as context variables
//           model.addAttribute("request", request);
//           //model.addAttribute("session", session);
//           model.addAttribute("servletContext", servletContext);
//           
//           return "signup";
//		}
//		
//		
//	}
//	
	
	
}
