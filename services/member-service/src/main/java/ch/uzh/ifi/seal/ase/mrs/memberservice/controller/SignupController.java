package ch.uzh.ifi.seal.ase.mrs.memberservice.controller;

import ch.uzh.ifi.seal.ase.mrs.memberservice.model.User;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.dto.UserDto;
import ch.uzh.ifi.seal.ase.mrs.memberservice.service.ISignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Rest Controller which handles Signing Up
 */
@RestController
@RequestMapping("api/signup")
@CrossOrigin(origins = "${HOSTS.GUI}")
public class SignupController {

    private final ISignupService signupService;

    /**
     * Constructor, autowires the signup service
     * @param signupService Signup service that helps creating users
     */
    @Autowired
    public SignupController(ISignupService signupService) {
        this.signupService = signupService;
    }

    /**
     * Check if username is available
     * @param username username to check
     * @return true if available, false if not available
     */
    @GetMapping("/checkUsernameAvailable/{username}")
    public boolean checkUsernameAvailable(@PathVariable("username") String username) {
        return signupService.checkUsernameAvailable(username);
    }

    /**
     * Creates a new user
     * @param user User to be created
     * @return newly created user with password field removed for security reasons
     */
    @PostMapping
    public User createUser(@RequestBody UserDto user){
        User newUser = signupService.createUser(user);
        newUser.setPassword(null);
        return newUser;
    }
}
