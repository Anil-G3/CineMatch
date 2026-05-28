package com.cinematch.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cinematch.dto.WatchlistRequest;
import com.cinematch.dto.WatchlistResponse;
import com.cinematch.entity.Movie;
import com.cinematch.entity.User;
import com.cinematch.entity.Watchlist;
import com.cinematch.repository.MovieRepository;
import com.cinematch.repository.WatchlistRepository;
import com.cinematch.service.WatchlistService;
import com.cinematch.util.AuthUtil;

@Service
public class WatchlistServiceImpl implements WatchlistService {
	
	private AuthUtil authUtil;
	private WatchlistRepository watchlistRepository;
	private MovieRepository movieRepository;

	public WatchlistServiceImpl(AuthUtil authUtil, WatchlistRepository watchlistRepository, MovieRepository movieRepository) {
		super();
		this.authUtil = authUtil;
		this.watchlistRepository = watchlistRepository;
		this.movieRepository = movieRepository;
	}

	@Override
	public String addToWatchlist(WatchlistRequest request) {
		
		User currentUser = authUtil.getCurrentUser();
		
		Movie movie =  movieRepository.findById(request.getMovieId()).orElseThrow(() -> new RuntimeException("Movie not found"));
		
		watchlistRepository.findByUserIdAndMovieId(currentUser.getId(), movie.getId())
					.ifPresent(w -> {throw new RuntimeException("Movie already in watchlist");
						});
		
		watchlistRepository.save(Watchlist.builder()
		.user(currentUser)
		.movie(movie)
		.addedAt(LocalDateTime.now())
		.build());
		
		return "Movie added to watchlist successfully";
	}

	@Override
	public String removeFromWatchlist(Long movieId) {

		User currentUser = authUtil.getCurrentUser();
		
		Watchlist watchlist = watchlistRepository.findByUserIdAndMovieId(currentUser.getId(), movieId)
			.orElseThrow(() -> new RuntimeException("Movie is not in Watchlist"));
		
		watchlistRepository.delete(watchlist);
		
		return "Movie removed from watchlist successfully";
	}

	@Override
	public List<WatchlistResponse> getMyWatchlist() {
		
		User currentUser = authUtil.getCurrentUser();
		
		List<Watchlist> watchlists =  watchlistRepository.findByUserId(currentUser.getId());
		
		List<WatchlistResponse> response = new ArrayList<>();
		
		for (Watchlist watchlist: watchlists) response.add(convertToResponse(watchlist));
		
		return response;
	}
	
	private WatchlistResponse convertToResponse(Watchlist watchlist) {
	    return new WatchlistResponse(
	        watchlist.getId(),
	        watchlist.getMovie().getId(),
	        watchlist.getMovie().getTitle(),
	        watchlist.getMovie().getPosterUrl(),
	        watchlist.getAddedAt()
	    );
	}

}
