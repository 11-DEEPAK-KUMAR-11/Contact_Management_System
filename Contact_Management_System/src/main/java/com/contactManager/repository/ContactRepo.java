package com.contactManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contactManager.models.Contact;

public interface ContactRepo extends JpaRepository<Contact, Integer>{

	public Contact findByEmail(String email);
}
