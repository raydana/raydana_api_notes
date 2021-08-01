package com.raydana.notes.config;

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
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
		String principalUserName = String.valueOf(auth.getPrincipal());
		String principalPassword = String.valueOf(auth.getCredentials());
		if (principalUserName == null || principalPassword == null || principalUserName.trim().isEmpty() || principalPassword.trim().isEmpty()) {
			throw new BadCredentialsException("");
		}
		String userName = principalUserName.trim();
		try {
			User user = userService.loadUserByUsername(userName);
			if (user != null && user.getPassword().equals(principalPassword)) {
				return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
			}
			else
				throw new BadCredentialsException("username or password is invalid");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BadCredentialsException("");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
