package ch.uzh.ifi.seal.ase.mrs.memberservice.service.impl;

import ch.uzh.ifi.seal.ase.mrs.memberservice.exception.GeneralWebserviceException;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.User;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.dto.UserDto;
import ch.uzh.ifi.seal.ase.mrs.memberservice.repository.UserRepository;
import ch.uzh.ifi.seal.ase.mrs.memberservice.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service that handles login requests
 */
@Service
public class LoginServiceImpl implements ILoginService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Constructor, autowires the UserRepository and creates BCryptPasswordEncoder
     *
     * @param userRepository user repository
     */
    @Autowired
    public LoginServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Login attempt of a user
     *
     * @param user user credentials to login
     * @return user object.
     */
    @Override
    public User login(UserDto user) {
        GeneralWebserviceException badCredentialsException = GeneralWebserviceException.builder().status(HttpStatus.UNAUTHORIZED).errorCode("C001").message("Bad Credentials. Username or password are not valid").build();
        User userDb = userRepository.findByUsername(user.getUsername()).orElseThrow(() -> badCredentialsException);
        if (passwordEncoder.matches(user.getPassword(), userDb.getPassword())) {
            return userDb;
        }
        throw badCredentialsException;
    }
}
