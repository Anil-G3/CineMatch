package com.cinematch.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cinematch.dto.MovieResponse;
import com.cinematch.service.RecommendationService;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

	private RecommendationService recommendationService;

	public RecommendationController(RecommendationService recommendationService) {
		super();
		this.recommendationService = recommendationService;
	}
	
	@GetMapping
	public ResponseEntity<List<MovieResponse>> getRecommendations() {
		
		List<MovieResponse> response =  recommendationService.getRecommendations();
		
		
		return ResponseEntity.ok(response);
	}
	
}
