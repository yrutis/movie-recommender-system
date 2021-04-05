package ch.uzh.ifi.seal.ase.mrs.memberservice.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * MovieRatingTest
 */
public class MovieRatingTest {

    /**
     * Test the MovieRating Builder and Getter
     */
    @Test
    void testMovieRatingBuilderGetter() {
        MovieRating movieRating = MovieRating.builder().rating(4.1).tmdbId(1L).build();
        Assertions.assertEquals(1L, movieRating.getTmdbId());
        Assertions.assertEquals(4.1, movieRating.getRating());
    }
}
