package ch.uzh.ifi.seal.ase.mrs.memberservice.service;

import ch.uzh.ifi.seal.ase.mrs.memberservice.exception.GeneralWebserviceException;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.User;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.dto.UserDto;
import ch.uzh.ifi.seal.ase.mrs.memberservice.repository.UserRepository;
import ch.uzh.ifi.seal.ase.mrs.memberservice.service.impl.LoginServiceImpl;
import ch.uzh.ifi.seal.ase.mrs.memberservice.service.impl.SignupServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test SignupServiceImpl
 */
@ExtendWith(MockitoExtension.class)
public class LoginServiceImplTest {

    @InjectMocks
    LoginServiceImpl loginService;

    @Mock
    UserRepository userRepository;


    /**
     * Test valid login
     */
    @Test
    public void loginValid() {
        UserDto loginUser = UserDto.builder().username("john").password("abcd").build();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User dbUser = User.builder().id(1L).password(encoder.encode("abcd")).username("john").build();
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(dbUser));
        User result = loginService.login(loginUser);
        Assertions.assertTrue(encoder.matches("abcd", result.getPassword()));
        Assertions.assertEquals(loginUser.getUsername(), result.getUsername());
    }

    /**
     * Test invalid login when user does not exist
     */
    @Test
    public void loginInvalidUserNotExists() {
        UserDto loginUser = UserDto.builder().username("john").password("abcd").build();
        when(userRepository.findByUsername("john")).thenReturn(Optional.empty());
        Assertions.assertThrows(GeneralWebserviceException.class, () -> {
            User result = loginService.login(loginUser);
        });
    }

    /**
     * Test invalid login when password incorrec
     */
    @Test
    public void loginInvalidPassword() {
        UserDto loginUser = UserDto.builder().username("john").password("abcd").build();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User dbUser = User.builder().id(1L).password(encoder.encode("1234")).username("john").build();
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(dbUser));
        Assertions.assertThrows(GeneralWebserviceException.class, () -> {
            User result = loginService.login(loginUser);
        });
    }
}
