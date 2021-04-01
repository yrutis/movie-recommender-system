package ch.uzh.ifi.seal.ase.mrs.memberservice.service;

import ch.uzh.ifi.seal.ase.mrs.memberservice.model.User;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.dto.UserDto;

/**
 * Interface of Login Service
 */
public interface ILoginService {

    /**
     * Login attempt of a user
     * @param user user credentials to login
     * @return user object.
     */
    User login(UserDto user);
}
