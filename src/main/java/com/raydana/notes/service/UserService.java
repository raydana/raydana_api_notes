package com.raydana.notes.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.raydana.notes.model.User;

public interface UserService {
	
	public User loadUserByUsername(String username) throws UsernameNotFoundException;
	
}
