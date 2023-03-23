package com.contactManager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.contactManager.models.Contact;

public interface ContactRepo extends JpaRepository<Contact, Integer>{

	public Contact findByEmail(String email);
	
	//pagination
	@Query("from Contact as c where c.user.id=:userId")
	public List<Contact> findContactByUser(@Param("userId") int userId);
}
