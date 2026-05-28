package com.cinematch.service;

import com.cinematch.dto.AuthResponse;
import com.cinematch.dto.LoginRequest;
import com.cinematch.dto.RegisterRequest;

public interface AuthService {

	String register(RegisterRequest request);
	AuthResponse login(LoginRequest request);
	
}
