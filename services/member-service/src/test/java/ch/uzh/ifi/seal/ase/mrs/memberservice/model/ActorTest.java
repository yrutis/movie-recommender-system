package ch.uzh.ifi.seal.ase.mrs.memberservice.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test Actor Class
 */
public class ActorTest {
    /**
     * Test the Actor Builder
     */
    @Test
    void testActorBuilder() {
        Actor actor = Actor.builder()
                .id(1L)
                .tmdbId(4L)
                .top1Movie(1L)
                .top2Movie(2L)
                .top3Movie(3L)
                .score(5.0)
                .build();
        Assertions.assertEquals(1L, actor.getId());
        Assertions.assertEquals(4L, actor.getTmdbId());
        Assertions.assertEquals(1L, actor.getTop1Movie());
        Assertions.assertEquals(2L, actor.getTop2Movie());
        Assertions.assertEquals(3L, actor.getTop3Movie());
        Assertions.assertEquals(5.0, actor.getScore());
    }

    /**
     * Test No Args
     */
    @Test
    void testActorNoArgs() {
        Actor actor = new Actor();
        Assertions.assertNotNull(actor);
    }

}
