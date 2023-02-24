package com.contactManagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contactManagement.model.Customer;

public interface CustomerDao extends JpaRepository<Customer, Integer> {

	public Customer findByMobileNo(String mobileNo);
}
