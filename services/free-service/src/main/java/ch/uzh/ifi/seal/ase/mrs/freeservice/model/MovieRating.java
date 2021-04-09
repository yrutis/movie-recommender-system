package ch.uzh.ifi.seal.ase.mrs.freeservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
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

    @JsonProperty("movieId")
    public long getTmdbId() {
        return this.tmdbId;
    }

    /**
     * Rating of the movie
     */
    private final double rating;

    @JsonProperty("my_rating")
    public int getRating() {
        return (int)this.rating;
    }
}
