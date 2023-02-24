package com.contactManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.contactManagement.exception.CustomerException;
import com.contactManagement.model.Customer;
import com.contactManagement.service.CustomerService;

@RestController
@RequestMapping("/API")
public class CutomerController {

	@Autowired
	private CustomerService cService;
	
	
	@PostMapping("/customers")
	public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) throws CustomerException {
		
		Customer savedCustomer= cService.createCustomer(customer);
		
		
		return new ResponseEntity<Customer>(savedCustomer,HttpStatus.CREATED);
	}
	
	@PutMapping("/customers")
	public  ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer,@RequestParam(required = false) String key ) throws CustomerException {
		
		
		Customer updatedCustomer= cService.updateCustomer(customer, key);
				
		return new ResponseEntity<Customer>(updatedCustomer,HttpStatus.OK);
		
	}
	
	
}
