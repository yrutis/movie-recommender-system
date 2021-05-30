package ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * Test TmdbCast
 */
public class TmdbCastTest {

    /**
     * Test No Args
     */
    @Test
    void testTmdbCastNoArgs() {
        TmdbCast tmdbCast = new TmdbCast();
        Assertions.assertNotNull(tmdbCast);
    }

    /**
     * Test setter
     */
    @Test
    void testTmdbCastSetter() {
        TmdbCast tmdbCast = new TmdbCast();
        tmdbCast.setCast(Arrays.asList(CastMember.builder().character("C3").name("N3").order(3).build(), CastMember.builder().character("C4").name("N4").order(4).build()));

        Assertions.assertEquals("C3", tmdbCast.getCast().get(0).getCharacter());
        Assertions.assertEquals("N3", tmdbCast.getCast().get(0).getName());
        Assertions.assertEquals(3, tmdbCast.getCast().get(0).getOrder());
    }
}
