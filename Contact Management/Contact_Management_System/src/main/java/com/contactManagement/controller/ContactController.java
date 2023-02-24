package com.contactManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contactManagement.exception.ContactException;
import com.contactManagement.model.Contacts;
import com.contactManagement.service.ContactService;

@RestController
@RequestMapping("/API")
public class ContactController {

	@Autowired
	ContactService cService;
	
	@GetMapping("/welcome")
	public ResponseEntity<String> welcomeTo()
	{ 
		String msg="Welcome to the Contact Management System";
		return new ResponseEntity<>(msg,HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/contact")
	public ResponseEntity<Contacts> addNew(@RequestBody Contacts contact) throws ContactException
	{
		Contacts res=cService.addNewContact(contact);
		
		return new ResponseEntity<>(res,HttpStatus.CREATED);
	}
	
	@PostMapping("/contact/{num}")
	public ResponseEntity<String> updateContact(@PathVariable("num") Integer cid,@RequestBody Contacts contact) throws ContactException
	{
		String msg=cService.updateContactByContactId(cid, contact);
		return new ResponseEntity<>(msg,HttpStatus.CREATED);
	}
	
	
	@GetMapping("/contact/{name}")
	public ResponseEntity<Contacts> findContactByName(@PathVariable("name") String name) throws ContactException
	{
		Contacts res=cService.getContactByName(name);
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	
	@GetMapping("/contact/{num}")
	public ResponseEntity<String> deleteContactByContactId(@PathVariable("num") Integer cid) throws ContactException
	{
		String msg=cService.deleteContactByContactId(cid);
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
