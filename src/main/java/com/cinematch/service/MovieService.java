package com.cinematch.service;

import java.util.List;

import com.cinematch.dto.MovieResponse;

public interface MovieService {

	List<MovieResponse> getAllMovies();
	MovieResponse getMovieById(Long id);
	List<MovieResponse> searchByTitle(String title);
	List<MovieResponse> filterByGenre(Long genreId);
	
}
