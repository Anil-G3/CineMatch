package com.cinematch.repository;

import com.cinematch.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByTitleContainingIgnoreCase(String title);
    
    @Query("""
    	    SELECT DISTINCT m
    	    FROM Movie m
    	    LEFT JOIN FETCH m.genres
    	    ORDER BY m.id
    	""")
    	List<Movie> findAllWithGenres();
    
    List<Movie> findByGenresId(Long genreId);

    @Query("""
    SELECT DISTINCT m FROM Movie m
    LEFT JOIN FETCH m.genres g
    WHERE g.id IN :genreIds
""")
List<Movie> findByGenresIdIn(@Param("genreIds") List<Long> genreIds);

}
