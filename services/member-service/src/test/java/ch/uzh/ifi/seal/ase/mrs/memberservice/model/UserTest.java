package ch.uzh.ifi.seal.ase.mrs.memberservice.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 * Test User Class
 */
public class UserTest {

    /**
     * Test the User Builder
     */
    @Test
    void testUserBuilder() {
        LocalDateTime ldt = LocalDateTime.now();
        User user = User.builder().id(1L).username("aUsername").password("aPassword").tblRatingUserId(1L).lastTrainedOn(ldt).build();
        Assertions.assertEquals(1L, user.getId());
        Assertions.assertEquals("aUsername", user.getUsername());
        Assertions.assertEquals("aPassword", user.getPassword());
        Assertions.assertEquals(1L, user.getTblRatingUserId());
        Assertions.assertEquals(ldt, user.getLastTrainedOn());
    }

    /**
     * Test the User All Args Constructor
     */
    @Test
    void testUserAllArgs() {
        LocalDateTime ldt = LocalDateTime.now();
        User user = new User(1L , "aUsername", "aPassword", 1L, ldt);
        Assertions.assertEquals(1L, user.getId());
        Assertions.assertEquals("aUsername", user.getUsername());
        Assertions.assertEquals("aPassword", user.getPassword());
        Assertions.assertEquals(1L, user.getTblRatingUserId());
        Assertions.assertEquals(ldt, user.getLastTrainedOn());
    }

    /**
     * Test the User No Args Constructor
     */
    @Test
    void testUserNoArgs() {
        User user = new User();
        Assertions.assertNull(user.getId());
        Assertions.assertNull(user.getUsername());
        Assertions.assertNull(user.getPassword());
        LocalDateTime ldt = LocalDateTime.now();
        user.setId(1L);
        user.setUsername("aUsername");
        user.setPassword("aPassword");
        user.setTblRatingUserId(1L);
        user.setLastTrainedOn(ldt);
        Assertions.assertEquals(1L, user.getId());
        Assertions.assertEquals("aUsername", user.getUsername());
        Assertions.assertEquals("aPassword", user.getPassword());
        Assertions.assertEquals(1L, user.getTblRatingUserId());
        Assertions.assertEquals(ldt, user.getLastTrainedOn());
    }
}
