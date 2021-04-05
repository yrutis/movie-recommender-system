package ch.uzh.ifi.seal.ase.mrs.memberservice.model.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test UserDto
 */
class UserDtoTest {

    /**
     * Test the UserDto Builder and Getters
     */
    @Test
    void testDto() {
        UserDto userDto = UserDto.builder().password("password").username("username").build();
        Assertions.assertEquals("username", userDto.getUsername());
        Assertions.assertEquals("password", userDto.getPassword());
    }
}
