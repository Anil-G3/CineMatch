package com.cinematch.serviceimpl;

import com.cinematch.entity.User;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cinematch.config.JwtUtil;
import com.cinematch.dto.AuthResponse;
import com.cinematch.dto.LoginRequest;
import com.cinematch.dto.RegisterRequest;
import com.cinematch.repository.UserRepository;
import com.cinematch.service.AuthService;
import com.cinematch.service.EmailService;

@Service
public class AuthServiceImpl implements AuthService {

	private UserRepository userRepository;

	private PasswordEncoder passwordEncoder;

	private JwtUtil jwtUtil;
	
	private EmailService emailService;

	public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, EmailService emailService) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
		this.emailService = emailService;
	}

	@Override
	public String register(RegisterRequest request) {
	
		
		if (userRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new RuntimeException("Email is already registered");
		}
		
		String encodedPassword = passwordEncoder.encode(request.getPassword());
		
		userRepository.save(User.builder()
		.name(request.getName())
		.email(request.getEmail())
		.password(encodedPassword)
		.createdAt(LocalDateTime.now())
		.build());

		emailService.sendWelcomeEmail(request.getEmail(), request.getName());
		
		return "User Registered Successfully";	
	}
	
	@Override
	public AuthResponse login(LoginRequest request) {
		
		User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("Email is not registered"));
		
		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) throw new RuntimeException("Invalid Password");
		 
		String token = jwtUtil.generateToken(request.getEmail());

		return new AuthResponse( "Login Successful", token, "Bearer", user.getName());
	}

}
