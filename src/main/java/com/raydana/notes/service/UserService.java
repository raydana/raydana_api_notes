package com.raydana.notes.service;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.raydana.notes.model.User;

public interface UserService {
	public User loadUserByUsername(String username) throws UsernameNotFoundException;
	public List<User> findAll();
}
