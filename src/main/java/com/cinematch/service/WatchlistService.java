package com.cinematch.service;

import java.util.List;

import com.cinematch.dto.WatchlistRequest;
import com.cinematch.dto.WatchlistResponse;

public interface WatchlistService {

	String addToWatchlist(WatchlistRequest request);
	String removeFromWatchlist(Long movieId);
	List<WatchlistResponse> getMyWatchlist();
	
}
