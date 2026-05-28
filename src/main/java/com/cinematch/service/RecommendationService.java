package com.cinematch.service;

import java.util.List;

import com.cinematch.dto.MovieResponse;

public interface RecommendationService {

	List<MovieResponse> getRecommendations();
	
}
