package com.contactManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contactManager.models.User;

public interface UserRepo extends JpaRepository<User, Integer>{

	public User findByEmail(String email);
}
