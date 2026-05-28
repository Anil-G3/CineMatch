package com.cinematch.repository;

import com.cinematch.entity.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {

    Optional<Watchlist> findByUserIdAndMovieId(Long userId, Long movieId);
    List<Watchlist> findByUserId(Long userId);

}