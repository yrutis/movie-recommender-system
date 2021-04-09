package ch.uzh.ifi.seal.ase.mrs.memberservice.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * ActorRatingTest
 */
public class ActorRatingTest {

    /**
     * Test the ActorRating Builder and Getter
     */
    @Test
    void testActorRatingBuilderGetter() {
        ActorRating movieRating = ActorRating.builder().rating(4.1).tmdbId(1L).build();
        Assertions.assertEquals(1L, movieRating.getTmdbId());
        Assertions.assertEquals(4.1, movieRating.getRating());
    }
}
