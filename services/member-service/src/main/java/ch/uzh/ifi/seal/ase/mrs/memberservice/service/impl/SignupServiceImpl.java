package ch.uzh.ifi.seal.ase.mrs.memberservice.service.impl;

import ch.uzh.ifi.seal.ase.mrs.memberservice.exception.GeneralWebserviceException;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.User;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.dto.UserDto;
import ch.uzh.ifi.seal.ase.mrs.memberservice.repository.RatingRepository;
import ch.uzh.ifi.seal.ase.mrs.memberservice.repository.UserRepository;
import ch.uzh.ifi.seal.ase.mrs.memberservice.service.ISignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service that handles requests regarding the signup process
 */
@Service
public class SignupServiceImpl implements ISignupService {

    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Constructor, autowires the UserRepository and creates BCryptPasswordEncoder
     * @param userRepository user repository
     */
    @Autowired
    public SignupServiceImpl(UserRepository userRepository, RatingRepository ratingRepository) {
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Checks if username is available
     * @param username username to check
     * @return true, if username is still available, else returns false
     */
    @Override
    public boolean checkUsernameAvailable(String username) {
        return !userRepository.findByUsername(username).isPresent();
    }

    /**
     * Creates a new user
     * @param user user to be created
     * @return newly created user
     */
    @Override
    public User createUser(UserDto user) {
        if (checkUsernameAvailable(user.getUsername())) {
            Long newId = this.ratingRepository.getHighestId() + 1;
            Optional<User> userWithId = this.userRepository.findByTblRatingUserId(newId);
            while (userWithId.isPresent()){
                newId++;
                userWithId = this.userRepository.findByTblRatingUserId(newId);
            }

            User userPersistence = User.builder().username(user.getUsername()).password(passwordEncoder.encode(user.getPassword())).tblRatingUserId(newId).build();

            return userRepository.save(userPersistence);
        } else {
            throw GeneralWebserviceException.builder().errorCode("UN001").message("Username is not available").status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
