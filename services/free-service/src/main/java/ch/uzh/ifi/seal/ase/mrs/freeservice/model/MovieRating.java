package ch.uzh.ifi.seal.ase.mrs.freeservice.model;

import lombok.Builder;
import lombok.Getter;


/**
 * DTO for storing a movie rating
 */
@Getter
@Builder
public class MovieRating {

    /**
     * TMDB Id of rated movie
     */
    private final long tmdbId;

    /**
     * Rating of the movie
     */
    private final double rating;
}
