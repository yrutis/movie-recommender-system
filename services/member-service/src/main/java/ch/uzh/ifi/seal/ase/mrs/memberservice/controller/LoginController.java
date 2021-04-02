package ch.uzh.ifi.seal.ase.mrs.memberservice.controller;

import ch.uzh.ifi.seal.ase.mrs.memberservice.model.User;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.dto.UserDto;
import ch.uzh.ifi.seal.ase.mrs.memberservice.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Rest Controller which handles Logging-In
 */
@RestController
@RequestMapping("api/login")
@CrossOrigin(origins = "${HOSTS.GUI}")
public class LoginController {

    private final ILoginService loginService;

    /**
     * Constructor, autowires the login service
     * @param loginService Login service
     */
    @Autowired
    public LoginController(ILoginService loginService) {
        this.loginService = loginService;
    }


    /**
     * login attempt from a user
     * @param user User to be logged in
     * @return User if login was successful
     */
    @PostMapping
    public User login(@RequestBody UserDto user){
        User newUser = loginService.login(user);
        newUser.setPassword(null);
        return newUser;
    }
}
