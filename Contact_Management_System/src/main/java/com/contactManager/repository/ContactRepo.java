package com.contactManager.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.contactManager.models.Contact;

public interface ContactRepo extends JpaRepository<Contact, Integer>{

	public Contact findByEmail(String email);
	
	//pagination
	@Query("from Contact as c where c.user.id=:userId")
	public Page<Contact> findContactByUser(@Param("userId") int userId, Pageable pageable);
	
	//Pageable interface will contains two things current page , contact per page(5)
}
