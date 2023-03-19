package com.contactManager.service;

import com.contactManager.models.User;

public interface UserService {

	public boolean isUserAlreadyPresent(String email);
	public void saveUser(User user);
}
