package ch.uzh.ifi.seal.ase.mrs.memberservice.model.dto;

import lombok.Builder;
import lombok.Getter;


/**
 * DTO for storing a movie rating
 */
@Getter
@Builder
public class MovieRatingDto {

    /**
     * TMDB Id of rated movie
     */
    private final long tmdbId;

    /**
     * Rating of the movie
     */
    private final double rating;
}
