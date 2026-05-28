package com.cinematch.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WatchlistResponse {

	private Long id;
	private Long movieId;
	private String title;
	private String posterUrl;
	private LocalDateTime addedAt;
	
	
}
