package com.cinematch.service;

import java.util.List;

import com.cinematch.dto.RatingRequest;
import com.cinematch.dto.RatingResponse;

public interface RatingService {

	String rateMovie(RatingRequest request);
	List<RatingResponse> getRatingsByMovie(Long movieId);
	List<RatingResponse> getRatingsByUser();
	
}
