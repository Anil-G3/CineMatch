package com.cinematch.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieResponse {
    private Long id;
    private String title;
    private String director;
    private Integer releaseYear;
    private Integer durationMinutes;
    private String description;
    private String posterUrl;
    private Float averageRating;
    private Integer totalRatings;
    private List<String> genres;
}