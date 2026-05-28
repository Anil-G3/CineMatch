package com.cinematch.dto;

import lombok.Data;

@Data
public class RatingRequest {

	private Long movieId;
	private Integer score;
	private String review;
	
}
