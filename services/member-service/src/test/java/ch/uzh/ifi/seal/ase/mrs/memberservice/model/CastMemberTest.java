package ch.uzh.ifi.seal.ase.mrs.memberservice.model;

import ch.uzh.ifi.seal.ase.mrs.memberservice.model.tmdb.CastMember;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test CastMemberTest
 */
public class CastMemberTest {

    /**
     * Test No Args
     */
    @Test
    void testCastMemberNoArgs() {
        CastMember castMember = new CastMember();
        Assertions.assertNotNull(castMember);
    }

    /**
     * Test setter
     */
    @Test
    void testCastMemberSetter() {
        CastMember castMember = new CastMember();
        castMember.setCharacter("c1");
        castMember.setName("n1");
        castMember.setOrder(1);
        Assertions.assertEquals(1, castMember.getOrder());
        Assertions.assertEquals("n1", castMember.getName());
        Assertions.assertEquals("c1", castMember.getCharacter());
    }
}
