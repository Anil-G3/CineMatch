package com.cinematch.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cinematch.dto.MovieResponse;
import com.cinematch.entity.Genre;
import com.cinematch.entity.Movie;
import com.cinematch.repository.MovieRepository;
import com.cinematch.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService {

	private MovieRepository movieRepository;
	
	public MovieServiceImpl(MovieRepository movieRepository) {
		super();
		this.movieRepository = movieRepository;
	}

	@Override
	public List<MovieResponse> getAllMovies() {

		List<Movie> movies = movieRepository.findAll();

		List<MovieResponse> response = new ArrayList<>();
		
		for (Movie movie : movies) response.add(convertToResponse(movie));
		
		return response;
	}

	@Override
	public MovieResponse getMovieById(Long id) {
		
		Movie movie = movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
		
		MovieResponse response = convertToResponse(movie);
		
		return response;
	}

	@Override
	public List<MovieResponse> searchByTitle(String title) {

		List<Movie> movies = movieRepository.findByTitleContainingIgnoreCase(title);
		
		List<MovieResponse> response = new ArrayList<>();
		
		for (Movie movie : movies) response.add(convertToResponse(movie));
		
		return response;
	}

	@Override
	public List<MovieResponse> filterByGenre(Long genreId) {
		
		List<Movie> movies = movieRepository.findByGenresId(genreId);
		
		List<MovieResponse> response = new ArrayList<>();		
		
		for (Movie movie : movies) response.add(convertToResponse(movie)) ;
		
		return response;
	}
	
	

	
	private MovieResponse convertToResponse(Movie movie) {
		
		List<String> genreNames = movie.getGenres()
				.stream()
				.map(Genre :: getName)
				.collect(Collectors.toList());

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
