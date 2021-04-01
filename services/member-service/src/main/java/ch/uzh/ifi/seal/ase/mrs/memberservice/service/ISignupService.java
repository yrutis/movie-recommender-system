package ch.uzh.ifi.seal.ase.mrs.memberservice.service;

import ch.uzh.ifi.seal.ase.mrs.memberservice.model.User;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.dto.UserDto;

/**
 * Interface of Signup Service
 */
public interface ISignupService {
    /**
     * Checks if username is available
     * @param username username to check
     * @return true, if username is still available, else returns false
     */
    boolean checkUsernameAvailable(String username);

    /**
     * Creates a new user
     * @param user user to be created
     * @return newly created user
     */
    User createUser(UserDto user);
}
