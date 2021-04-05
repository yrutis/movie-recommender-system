package ch.uzh.ifi.seal.ase.mrs.freeservice.model;

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
        ActorRating movieRating = ActorRating.builder().rating(4.1).actorId(1L).build();
        Assertions.assertEquals(1L, movieRating.getActorId());
        Assertions.assertEquals(4.1, movieRating.getRating());
    }
}
