package com.cinematch.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cinematch.dto.RatingRequest;
import com.cinematch.dto.RatingResponse;
import com.cinematch.service.RatingService;

@RestController
@RequestMapping("/ratings")
public class RatingController {

	private RatingService ratingService;

	public RatingController(RatingService ratingService) {
		super();
		this.ratingService = ratingService;
	}

	@PostMapping
	public ResponseEntity<Map<String, String>> rateMovie(@RequestBody RatingRequest request) {

		String message = ratingService.rateMovie(request);

		Map<String, String> response = new HashMap<>();
		response.put("message", message);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/movie/{movieId}")
	public ResponseEntity<List<RatingResponse>> getRatingsByMovie(@PathVariable Long movieId) {

		List<RatingResponse> response = ratingService.getRatingsByMovie(movieId);
		
		return ResponseEntity.ok(response);

	}
	
	@GetMapping("/my")
	public ResponseEntity<List<RatingResponse>> getRatingsByUser() {

	    List<RatingResponse> response = ratingService.getRatingsByUser();

	    return ResponseEntity.ok(response);

	}

}
