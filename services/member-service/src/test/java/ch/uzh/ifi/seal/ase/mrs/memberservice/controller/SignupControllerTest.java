package ch.uzh.ifi.seal.ase.mrs.memberservice.controller;

import ch.uzh.ifi.seal.ase.mrs.memberservice.exception.GeneralWebserviceException;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.User;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.dto.UserDto;
import ch.uzh.ifi.seal.ase.mrs.memberservice.service.ISignupService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;

/**
 * Test SignupController
 */
@ExtendWith(MockitoExtension.class)
public class SignupControllerTest {
    @InjectMocks
    SignupController signupController;

    @Mock
    ISignupService signupService;

    /**
     * Test checkUsernameAvailable
     */
    @Test
    public void testCheckUsername()
    {
        when(signupService.checkUsernameAvailable(eq("admin"))).thenReturn(true);
        when(signupService.checkUsernameAvailable(eq("john"))).thenReturn(false);
        Assertions.assertTrue(signupController.checkUsernameAvailable("admin"));
        Assertions.assertFalse(signupController.checkUsernameAvailable("john"));
        when(signupService.checkUsernameAvailable(eq("john"))).thenReturn(true);
        Assertions.assertTrue(signupController.checkUsernameAvailable("john"));
    }

    /**
     * Test createUser
     */
    @Test
    public void testCreateUser()
    {
        User user = User.builder().id(1L).password("1234").username("admin").build();
        when(signupService.createUser(any(UserDto.class))).thenReturn(user);
        UserDto requestUser = UserDto.builder().password("1234").username("admin").build();
        User result = signupController.createUser(requestUser);
        Assertions.assertNull(result.getPassword());
        Assertions.assertEquals(result.getUsername(), requestUser.getUsername());
        Assertions.assertNotNull(result.getId());
    }

    /**
     * Test createUser with wrong data
     */
    @Test
    public void testCreateUserException()
    {
        UserDto user = UserDto.builder().password("1234").username("admin").build();
        GeneralWebserviceException exception = GeneralWebserviceException.builder().build();
        when(signupService.createUser(any(UserDto.class))).thenThrow(exception);
        Assertions.assertThrows(GeneralWebserviceException.class, () -> {
            signupController.createUser(user);
        });
    }

}
