package com.contactManager.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.contactManager.models.Contact;
import com.contactManager.models.User;
import com.contactManager.repository.ContactRepo;
import com.contactManager.repository.UserRepo;


@RestController
public class SearchController {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ContactRepo contactRepo;
	
	@GetMapping("/search/{query}")
	public ResponseEntity<?> searchHandler( @PathVariable("query") String query, Principal principal )
	{
		System.out.println(query);
		
		User user=userRepo.findByEmail(principal.getName());
		
		List<Contact> contacts=contactRepo.findByNameContainingAndUser(query, user); 
		
		return  ResponseEntity.ok(contacts);
	}
}
