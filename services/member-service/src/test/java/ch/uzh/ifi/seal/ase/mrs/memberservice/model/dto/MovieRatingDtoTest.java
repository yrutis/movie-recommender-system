package ch.uzh.ifi.seal.ase.mrs.memberservice.model.dto;

import ch.uzh.ifi.seal.ase.mrs.memberservice.model.dto.MovieRatingDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * MovieRatingTest
 */
public class MovieRatingDtoTest {

    /**
     * Test the MovieRating Builder and Getter
     */
    @Test
    void testMovieRatingBuilderGetter() {
        MovieRatingDto movieRatingDto = MovieRatingDto.builder().rating(4.1).tmdbId(1L).build();
        Assertions.assertEquals(1L, movieRatingDto.getTmdbId());
        Assertions.assertEquals(4.1, movieRatingDto.getRating());
    }
}
