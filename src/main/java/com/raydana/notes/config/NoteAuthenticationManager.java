package com.raydana.notes.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.raydana.notes.model.User;
import com.raydana.notes.service.UserService;

@Component
public class NoteAuthenticationManager implements AuthenticationProvider {

	@Autowired
	private UserService userService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		System.out.println("test");
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
		String username = String.valueOf(auth.getPrincipal());
		String password = String.valueOf(auth.getCredentials());
		System.out.println(username);
		System.out.println(password);
		if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
			throw new BadCredentialsException("");
		}

		String userName = username.trim();

		String correctPassword = "";
		String userSalt = "";

		try {
			User users = userService.loadUserByUsername(userName);
			if (users != null) {
				//correctPassword = users.getPassword();
				//userSalt = users.getSalt();
				//String newPass = PassEncryptDecryptUtil.get_SHA_512_SecurePassword(password, userSalt);
				//if (!correctPassword.equals(newPass) && !correctPassword.equals(password)) {
				//	throw new BadCredentialsException(LanguageUtil.get("login.userpassNotValid"));
				//}
				//users.setPassword("");
				
				return new UsernamePasswordAuthenticationToken(users, null, users.getAuthorities());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BadCredentialsException("");
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}



}
