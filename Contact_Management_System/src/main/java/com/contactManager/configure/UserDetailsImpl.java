package com.contactManager.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.contactManager.models.User;
import com.contactManager.repository.UserRepo;

public class UserDetailsImpl implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// Fetching user from database
		User user=userRepo.findByEmail(username);
		
		if(user==null)
		{
			throw new UsernameNotFoundException("Could not found user !");
		}
		
		CustomUserDetails custom=new CustomUserDetails(user);
		
		return custom;
	}

	
	
}
