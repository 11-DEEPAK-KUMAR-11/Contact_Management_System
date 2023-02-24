package com.contactManagement.service;



import com.contactManagement.exception.CustomerException;
import com.contactManagement.model.Customer;

public interface CustomerService {

    public Customer createCustomer(Customer customer)throws CustomerException;
	
	public Customer updateCustomer(Customer customer,String key)throws CustomerException;
	
	
}
