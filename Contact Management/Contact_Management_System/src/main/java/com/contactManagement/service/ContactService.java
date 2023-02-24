package com.contactManagement.service;

import com.contactManagement.exception.ContactException;
import com.contactManagement.model.Contacts;

public interface ContactService {

	public Contacts addNewContact(Contacts contact) throws ContactException;
	
	public String updateContactByContactId(Integer cid,Contacts contact) throws ContactException;
	
	public String deleteContactByContactId(Integer cid) throws ContactException;
	
	public Contacts getContactByName(String name) throws ContactException;
}
