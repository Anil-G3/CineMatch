package com.cinematch.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="movies")
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private String title;
	
	@Column(nullable=false)
	private String director;
	
	@Column(name="release_year", nullable=false)
	private Integer releaseYear;
	
	@Column(name="duration_min", nullable=false)
	private Integer durationMinutes;
	
	@Column(nullable=false, columnDefinition="TEXT")
	private String description;
	
	@Column(name="poster_url", nullable=false)
	private String posterUrl;
	
	@Column(name="average_rating")
	@Builder.Default
	private Float averageRating = 0.0f;
	
	@Column(name="total_ratings")
	@Builder.Default
	private Integer totalRatings = 0;
	
	@ManyToMany
	@JoinTable(
	    name = "movie_genres",
	    joinColumns = @JoinColumn(name = "movie_id"),
	    inverseJoinColumns = @JoinColumn(name = "genre_id")
	)
	@Builder.Default
	private List<Genre> genres = new ArrayList<>();
	
}
