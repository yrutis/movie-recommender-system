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
                .id(1L)
                .tmdbId(1L)
                .imdbId("1")
                .score(5.0)
                .build();
        Assertions.assertEquals(1L, movie.getId());
        Assertions.assertEquals(1L, movie.getTmdbId());
        Assertions.assertEquals("1", movie.getImdbId());
        Assertions.assertEquals(5.0, movie.getScore());
    }

    /**
     * Test No Args
     */
    void testMovieNoArgs() {
        Movie movie = new Movie();
        Assertions.assertNotNull(movie);
    }

}
