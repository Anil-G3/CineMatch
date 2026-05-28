package com.cinematch.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileResponse {

	private String name;
	private String email;
	private LocalDateTime memberSince;
	private List<String> preferredGenres;
	private Integer watchlistCount;
	private Integer totalRatings;
	
}
