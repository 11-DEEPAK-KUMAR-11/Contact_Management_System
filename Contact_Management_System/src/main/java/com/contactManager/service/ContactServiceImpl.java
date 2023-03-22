package com.contactManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contactManager.models.Contact;

import com.contactManager.repository.ContactRepo;


@Service
public class ContactServiceImpl implements ContactService{

	@Autowired
    private ContactRepo cRepository;
	
	@Override
	public boolean isUserAlreadyPresent(String email) {
		
		Contact contact = cRepository.findByEmail(email);
        return contact != null;
	}

}
