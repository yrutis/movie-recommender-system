package ch.uzh.ifi.seal.ase.mrs.memberservice.model.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * UserDto class to receive login and signup requests
 */

@Getter
@Builder
public class UserDto {
    /**
     * Username of the User
     */
    private final String username;

    /**
     * Password of the User
     */
    private final String password;
}
