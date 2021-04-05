package ch.uzh.ifi.seal.ase.mrs.freeservice.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * Test RatingDto
 */
public class RatingDtoTest {
    /**
     * Test the RatingDto Builder and Getter
     */
    @Test
    void testRatingDtoBuilderGetter() {
        RatingDto ratingDto = RatingDto.builder().actorRatings(new ArrayList<>()).movieRatings(new ArrayList<>()).build();
        Assertions.assertEquals(0, ratingDto.getActorRatings().size());
        Assertions.assertEquals(0, ratingDto.getMovieRatings().size());
    }

}
