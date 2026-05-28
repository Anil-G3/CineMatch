package com.cinematch.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cinematch.dto.AuthResponse;
import com.cinematch.dto.LoginRequest;
import com.cinematch.dto.RegisterRequest;
import com.cinematch.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private AuthService authService;

	public AuthController(AuthService authService) {
		super();
		this.authService = authService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<Map<String, String>> register(@RequestBody RegisterRequest request) {
		
		String message = authService.register(request);

		Map<String, String> response = new HashMap<>();
		response.put("message", message);
		
		return ResponseEntity.ok(response);
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
		
		return  ResponseEntity.ok(authService.login(request));
	
	}
	
}
