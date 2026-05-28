package com.cinematch.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cinematch.entity.Genre;
import com.cinematch.repository.GenreRepository;
import com.cinematch.service.GenreService;

@Service
public class GenreServiceImpl implements GenreService {

	private GenreRepository genreRepository;

	public GenreServiceImpl(GenreRepository genreRepository) {
		super();
		this.genreRepository = genreRepository;
	}

	@Override
	public List<Genre> getAllGenres() {

		return genreRepository.findAll();

	}

}
