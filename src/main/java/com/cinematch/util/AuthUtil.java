package com.cinematch.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.cinematch.entity.User;

@Component
public class AuthUtil {

	public User getCurrentUser() {
	    return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
}
