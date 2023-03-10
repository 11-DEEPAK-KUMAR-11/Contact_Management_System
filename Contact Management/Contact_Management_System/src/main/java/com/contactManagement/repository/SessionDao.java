package com.contactManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contactManagement.model.CurrentUserSession;

public interface SessionDao extends JpaRepository<CurrentUserSession, Integer> {

	public  CurrentUserSession  findByUuid(String uuid);
}
