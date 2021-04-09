package ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * TmdbActorTest
 */
class TmdbActorTest {

    /**
     * Test the TmdbActorTest Builder
     */
    @Test
    void testActorBuilder() {
        TmdbActor tmdbActor = TmdbActor.builder().id(1L).name("Name").profilePath("Path").build();
        Assertions.assertEquals(1L, tmdbActor.getId());
        Assertions.assertEquals("Name", tmdbActor.getName());
        Assertions.assertEquals("Path", tmdbActor.getProfilePath());
        tmdbActor.setProfilePath("Path2");
        Assertions.assertEquals("Path2", tmdbActor.getProfilePath());
    }
}
