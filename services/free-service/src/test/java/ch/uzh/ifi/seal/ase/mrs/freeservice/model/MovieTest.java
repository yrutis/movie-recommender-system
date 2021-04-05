package ch.uzh.ifi.seal.ase.mrs.freeservice.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test Movie Class
 */
public class MovieTest {
    /**
     * Test the Movie Builder
     */
    @Test
    void testMovieBuilder() {
        Movie movie = Movie.builder()
                .id(1l)
                .tmdbId(1L)
                .imdbId("1")
                .voteCount(1)
                .voteAverage(5.5)
                .popularity(10.0)
                .build();
        Assertions.assertEquals(1l, movie.getId());
        Assertions.assertEquals(1L, movie.getTmdbId());
        Assertions.assertEquals("1", movie.getImdbId());
        Assertions.assertEquals(1, movie.getVoteCount());
        Assertions.assertEquals(5.5, movie.getVoteAverage());
        Assertions.assertEquals(10.0, movie.getPopularity());
    }

    /**
     * Test No Args
     */
    void testMovieNoArgs() {
        Movie movie = new Movie();
        Assertions.assertNotNull(movie);
    }

}
