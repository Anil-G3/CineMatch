package com.cinematch.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cinematch.dto.MovieResponse;
import com.cinematch.service.MovieService;

@RestController
@RequestMapping("/movies")
public class MovieController {

	private MovieService movieService;

	public MovieController(MovieService movieService) {
		super();
		this.movieService = movieService;
	}
	
	@GetMapping
	public ResponseEntity<List<MovieResponse>> getAllMovies() {
		
		List<MovieResponse> movies = movieService.getAllMovies();
		
		return ResponseEntity.ok(movies);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<MovieResponse> getMovieById(@PathVariable Long id) {
		
		MovieResponse movie = movieService.getMovieById(id);
		
		return ResponseEntity.ok(movie);
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<MovieResponse>> searchByTitle(@RequestParam String title) {
		
		List<MovieResponse> movies = movieService.searchByTitle(title);
		
		return ResponseEntity.ok(movies);
	}
	
	@GetMapping("/genre")
	public ResponseEntity<List<MovieResponse>> filterByGenre(@RequestParam Long genreId) {
		
		List<MovieResponse> movies = movieService.filterByGenre(genreId);
		
		return ResponseEntity.ok(movies);
	}
	
}
