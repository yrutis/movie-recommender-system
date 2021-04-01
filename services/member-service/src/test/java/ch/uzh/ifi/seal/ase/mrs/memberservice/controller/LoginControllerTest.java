package ch.uzh.ifi.seal.ase.mrs.memberservice.controller;

import ch.uzh.ifi.seal.ase.mrs.memberservice.exception.GeneralWebserviceException;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.User;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.dto.UserDto;
import ch.uzh.ifi.seal.ase.mrs.memberservice.service.ILoginService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.when;

/**
 * Test SignupController
 */
@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {
    @InjectMocks
    LoginController loginController;

    @Mock
    ILoginService loginService;

    /**
     * Test login valid
     */
    @Test
    public void testLoginValid() {
        UserDto loginUser = UserDto.builder().username("john").password("abcd").build();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User serviceUser = User.builder().id(1L).password(encoder.encode("1234")).username("john").build();
        when(loginService.login(loginUser)).thenReturn(serviceUser);
        User result = loginController.login(loginUser);
        Assertions.assertEquals(serviceUser.getId(), result.getId());
        Assertions.assertEquals(serviceUser.getUsername(), result.getUsername());
        Assertions.assertNull(result.getPassword());
    }

    /**
     * Test login invalid
     */
    @Test
    public void testLoginInvalid() {
        UserDto loginUser = UserDto.builder().username("john").password("abcd").build();
        GeneralWebserviceException badCredentialsException = GeneralWebserviceException.builder().status(HttpStatus.UNAUTHORIZED).errorCode("C001").message("Bad Credentials. Username or password are not valid").build();
        when(loginService.login(loginUser)).thenThrow(badCredentialsException);
        Assertions.assertThrows(GeneralWebserviceException.class, () -> {
            User result = loginController.login(loginUser);
        });
    }


}
