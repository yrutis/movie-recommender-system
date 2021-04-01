package ch.uzh.ifi.seal.ase.mrs.memberservice.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test User Class
 */
public class UserTest {

    /**
     * Test the User Builder
     */
    @Test
    void testUserBuilder() {
        User user = User.builder().id(1L).username("aUsername").password("aPassword").build();
        Assertions.assertEquals(1L, user.getId());
        Assertions.assertEquals("aUsername", user.getUsername());
        Assertions.assertEquals("aPassword", user.getPassword());
    }

    /**
     * Test the User All Args Constructor
     */
    @Test
    void testUserAllArgs() {
        User user = new User(1L , "aUsername", "aPassword");
        Assertions.assertEquals(1L, user.getId());
        Assertions.assertEquals("aUsername", user.getUsername());
        Assertions.assertEquals("aPassword", user.getPassword());
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

        user.setId(1L);
        user.setUsername("aUsername");
        user.setPassword("aPassword");
        Assertions.assertEquals(1L, user.getId());
        Assertions.assertEquals("aUsername", user.getUsername());
        Assertions.assertEquals("aPassword", user.getPassword());
    }
}
