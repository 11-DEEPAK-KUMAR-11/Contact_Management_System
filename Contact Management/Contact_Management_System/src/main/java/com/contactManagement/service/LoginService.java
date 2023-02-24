package com.contactManagement.service;

import com.contactManagement.exception.LoginException;
import com.contactManagement.model.LoginDTO;

public interface LoginService {

	public String logIntoAccount(LoginDTO dto)throws LoginException;

	public String logOutFromAccount(String key)throws LoginException;
}
