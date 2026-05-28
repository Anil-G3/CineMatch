package com.cinematch.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cinematch.dto.ProfileResponse;
import com.cinematch.service.ProfileService;

@RestController
@RequestMapping("/profile")
public class ProfileController {

	private ProfileService profileService;

	public ProfileController(ProfileService profileService) {
		super();
		this.profileService = profileService;
	}
	
	@GetMapping
	public ResponseEntity<ProfileResponse> getProfile() {
		
		ProfileResponse response = profileService.getProfile();
		
		return ResponseEntity.ok(response);
	}
	
}
