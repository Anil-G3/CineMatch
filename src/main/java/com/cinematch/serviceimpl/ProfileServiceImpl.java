package com.cinematch.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cinematch.dto.ProfileResponse;
import com.cinematch.entity.Genre;
import com.cinematch.entity.User;
import com.cinematch.repository.RatingRepository;
import com.cinematch.repository.UserRepository;
import com.cinematch.repository.WatchlistRepository;
import com.cinematch.service.ProfileService;
import com.cinematch.util.AuthUtil;

@Service
public class ProfileServiceImpl implements ProfileService {

	private AuthUtil authUtil;
	private UserRepository userRepository;
	private RatingRepository ratingRepository;
	private WatchlistRepository watchlistRepository;
	
	public ProfileServiceImpl(AuthUtil authUtil, UserRepository userRepository, RatingRepository ratingRepository,
			WatchlistRepository watchlistRepository) {
		super();
		this.authUtil = authUtil;
		this.userRepository = userRepository;
		this.ratingRepository = ratingRepository;
		this.watchlistRepository = watchlistRepository;
	}



	@Override
	public ProfileResponse getProfile() {
		
		User user = userRepository.findById(authUtil.getCurrentUser().getId())
				.orElseThrow(() -> new RuntimeException("User not found"));
		
		List<String> preferredGenres = user.getPreferredGenres()
				.stream()
				.map(Genre :: getName)
				.collect(Collectors.toList());
		
		int watchlistCount = watchlistRepository.findByUserId(user.getId()).size();
		
		int totalRatings = ratingRepository.findByUserId(user.getId()).size();
		
		ProfileResponse response = new ProfileResponse(user.getName(), user.getEmail(), user.getCreatedAt(), preferredGenres, watchlistCount, totalRatings);
		
		return response;

	}

}
