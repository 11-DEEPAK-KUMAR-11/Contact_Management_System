package com.contactManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contactManagement.model.Contacts;

public interface ContactRepo extends JpaRepository<Contacts, Integer> {

	public Contacts findByFullName(String name);
}
