package com.cinematch.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cinematch.dto.WatchlistRequest;
import com.cinematch.dto.WatchlistResponse;
import com.cinematch.service.WatchlistService;

@RestController
@RequestMapping("/watchlist")
public class WatchlistController {

	private WatchlistService watchlistService;

	public WatchlistController(WatchlistService watchlistService) {
		super();
		this.watchlistService = watchlistService;
	}
	
	@PostMapping
	public ResponseEntity<Map<String, String>> addToWatchlist(@RequestBody WatchlistRequest request) {
		
		Map<String, String> response = new HashMap<>();
		
		String message = watchlistService.addToWatchlist(request);
		response.put("message", message);
		
		return ResponseEntity.ok(response);
		
	}
	
	@DeleteMapping("/{movieId}")
	public ResponseEntity<Map<String, String>> removeFromWatchlist(@PathVariable Long movieId) {
		
		Map<String, String> response = new HashMap<>();
		String message = watchlistService.removeFromWatchlist(movieId);
		response.put("message", message);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/my")
	public ResponseEntity<List<WatchlistResponse>> getMyWatchlist() {
		
		List<WatchlistResponse> response =  watchlistService.getMyWatchlist();
		
		return ResponseEntity.ok(response);
	}
	
	
}
