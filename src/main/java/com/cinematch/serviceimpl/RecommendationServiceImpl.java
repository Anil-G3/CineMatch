package com.cinematch.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cinematch.dto.MovieResponse;
import com.cinematch.entity.Genre;
import com.cinematch.entity.Movie;
import com.cinematch.entity.User;
import com.cinematch.repository.MovieRepository;
import com.cinematch.repository.RatingRepository;
import com.cinematch.repository.UserRepository;
import com.cinematch.service.RecommendationService;
import com.cinematch.util.AuthUtil;

@Service
public class RecommendationServiceImpl implements RecommendationService {

	private AuthUtil authUtil;
	private UserRepository userRepository;
	private RatingRepository ratingRepository;
	private MovieRepository movieRepository;

	public RecommendationServiceImpl(AuthUtil authUtil, UserRepository userRepository,
			RatingRepository ratingRepository, MovieRepository movieRepository) {
		super();
		this.authUtil = authUtil;
		this.userRepository = userRepository;
		this.ratingRepository = ratingRepository;
		this.movieRepository = movieRepository;
	}

	@Override
	public List<MovieResponse> getRecommendations() {

	    User currentUser = userRepository.findById(authUtil.getCurrentUser().getId())
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    List<Genre> preferredGenres = currentUser.getPreferredGenres();

	    if (preferredGenres.isEmpty()) {
	        return new ArrayList<>();
	    }

	    List<Long> ratedMovieIds = ratingRepository.findByUserId(currentUser.getId())
	            .stream()
	            .map(rating -> rating.getMovie().getId())
	            .collect(Collectors.toList());

	    List<Movie> recommendedMovies = new ArrayList<>();

	    for (Genre genre : preferredGenres) {
	        List<Movie> moviesByGenre = movieRepository.findByGenresId(genre.getId());
	        for (Movie movie : moviesByGenre) {
	            if (!ratedMovieIds.contains(movie.getId())
	                    && !recommendedMovies.contains(movie)) {
	                recommendedMovies.add(movie);
	            }
	        }
	    }

	    List<MovieResponse> response = new ArrayList<>();
	    for (Movie movie : recommendedMovies) {
	        response.add(convertToResponse(movie));
	    }

	    return response;
	}
	
	private MovieResponse convertToResponse(Movie movie) {
	    List<String> genreNames = new ArrayList<>();
	    for (Genre genre : movie.getGenres()) {
	        genreNames.add(genre.getName());
	    }
	    return new MovieResponse(
	            movie.getId(),
	            movie.getTitle(),
	            movie.getDirector(),
	            movie.getReleaseYear(),
	            movie.getDurationMinutes(),
	            movie.getDescription(),
	            movie.getPosterUrl(),
	            movie.getAverageRating(),
	            movie.getTotalRatings(),
	            genreNames
	    );
	}

}
