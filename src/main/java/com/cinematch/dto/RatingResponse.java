package com.cinematch.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor 
public class RatingResponse {

	private Long id;
	private String username;
	private Long movieId;
	private Integer score;
	private String review;
	private LocalDateTime createdAt;
	
	
}
