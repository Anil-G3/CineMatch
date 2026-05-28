package com.cinematch.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cinematch.entity.Genre;
import com.cinematch.service.GenreService;

@RestController
@RequestMapping("/genres")
public class GenreController {

	private GenreService genreService;

	public GenreController(GenreService genreService) {
		super();
		this.genreService = genreService;
	}
	
	@GetMapping
	public ResponseEntity<List<Genre>> getAllGenres() {
		return ResponseEntity.ok(genreService.getAllGenres());
	}
	
}
