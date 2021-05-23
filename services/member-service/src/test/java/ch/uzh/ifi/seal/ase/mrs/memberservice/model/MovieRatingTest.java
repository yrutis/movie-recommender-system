package ch.uzh.ifi.seal.ase.mrs.memberservice.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test MovieRating Class
 */
public class MovieRatingTest {
    /**
     * Test the MovieRating
     */
    @Test
    void testMovieRatingBuilder() {
        MovieRating movieRating = MovieRating.builder().id(1L).rating(4.0).tmdbId(2L).userId(3L).build();
        Assertions.assertEquals(1L, movieRating.getId());
        Assertions.assertEquals(4.0, movieRating.getRating());
        Assertions.assertEquals(2L, movieRating.getTmdbId());
        Assertions.assertEquals(3L, movieRating.getUserId());
    }

    /**
     * Test No Args and Setter
     */
    @Test
    void testMovieRatingNoArgsAndSetter() {
        MovieRating movieRating = new MovieRating();
        Assertions.assertNotNull(movieRating);
        movieRating.setId(1L);
        movieRating.setRating(4.0);
        movieRating.setTmdbId(2L);
        movieRating.setUserId(3L);
        Assertions.assertEquals(1L, movieRating.getId());
        Assertions.assertEquals(4.0, movieRating.getRating());
        Assertions.assertEquals(2L, movieRating.getTmdbId());
        Assertions.assertEquals(3L, movieRating.getUserId());
    }



}
