package com.cinematch.serviceimpl;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cinematch.dto.RatingRequest;
import com.cinematch.dto.RatingResponse;
import com.cinematch.entity.Genre;
import com.cinematch.entity.Movie;
import com.cinematch.entity.Rating;
import com.cinematch.entity.User;
import com.cinematch.repository.MovieRepository;
import com.cinematch.repository.RatingRepository;
import com.cinematch.repository.UserRepository;
import com.cinematch.service.RatingService;
import com.cinematch.util.AuthUtil;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class RatingServiceImpl implements RatingService {

	private AuthUtil authUtil;
	private RatingRepository ratingRepository;
	private MovieRepository movieRepository;
	private UserRepository userRepository;

	public RatingServiceImpl(AuthUtil authUtil, RatingRepository ratingRepository, MovieRepository movieRepository, UserRepository userRepository) {
		super();
		this.authUtil = authUtil;
		this.ratingRepository = ratingRepository;
		this.movieRepository = movieRepository;
		this.userRepository = userRepository;
	}

	@Override
	public String rateMovie(RatingRequest request) {

		User currentUser = userRepository.findById(authUtil.getCurrentUser().getId())
			    .orElseThrow(() -> new RuntimeException("User not found"));

		Movie movie = movieRepository.findById(request.getMovieId())
				.orElseThrow(() -> new RuntimeException("Movie not found"));

		ratingRepository.findByUserIdAndMovieId(currentUser.getId(), movie.getId()).ifPresent(r -> {
			throw new RuntimeException("Already Rated");
		});

		Rating rating = Rating.builder().user(currentUser).movie(movie).score(request.getScore())
				.review(request.getReview()).createdAt(LocalDateTime.now()).build();

		ratingRepository.save(rating);
		
		if (request.getScore() >= 4) {
			
			List<Genre> movieGenres = movie.getGenres();
			List<Genre> preferredGenres = currentUser.getPreferredGenres();
			
			for (Genre genre : movieGenres) {
				if (!preferredGenres.contains(genre))
					preferredGenres.add(genre);
			}
			
			currentUser.setPreferredGenres(preferredGenres);
			userRepository.save(currentUser);
		}

		List<Rating> allRatings = ratingRepository.findByMovieIdOrderByCreatedAtDesc(movie.getId());
		double average = allRatings.stream().mapToInt(Rating::getScore).average().orElse(0.0);
		movie.setAverageRating((float) average);
		movie.setTotalRatings(allRatings.size());
		movieRepository.save(movie);

		return "Rating submitted successfully";
	}

	@Override
	public List<RatingResponse> getRatingsByMovie(Long movieId) {

		List<Rating> ratings = ratingRepository.findByMovieIdOrderByCreatedAtDesc(movieId);

		List<RatingResponse> response = new ArrayList<>();

		for (Rating rating : ratings)
			response.add(convertToResponse(rating));

		return response;
	}

	@Override
	public List<RatingResponse> getRatingsByUser() {

		User currentUser = authUtil.getCurrentUser();

		List<Rating> ratings = ratingRepository.findByUserId(currentUser.getId());

		List<RatingResponse> response = new ArrayList<>();

		for (Rating rating : ratings)
			response.add(convertToResponse(rating));

		return response;
	}

	private RatingResponse convertToResponse(Rating rating) {
		return new RatingResponse(rating.getId(), rating.getUser().getName(), rating.getMovie().getId(),
				rating.getScore(), rating.getReview(), rating.getCreatedAt());
	}

}
