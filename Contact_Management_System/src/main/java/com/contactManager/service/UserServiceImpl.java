package com.contactManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contactManager.models.User;
import com.contactManager.repository.UserRepo;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
    private UserRepo userRepository;

	@Override
	public boolean isUserAlreadyPresent(String email) {
		
		 User user = userRepository.findByEmail(email);
         return user != null;
	}

	@Override
	public void saveUser(User user) {

		userRepository.save(user);
		
	}


}
