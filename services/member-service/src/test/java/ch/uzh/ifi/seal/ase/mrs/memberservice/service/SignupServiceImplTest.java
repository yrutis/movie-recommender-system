package ch.uzh.ifi.seal.ase.mrs.memberservice.service;

import ch.uzh.ifi.seal.ase.mrs.memberservice.exception.GeneralWebserviceException;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.User;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.dto.UserDto;
import ch.uzh.ifi.seal.ase.mrs.memberservice.repository.RatingRepository;
import ch.uzh.ifi.seal.ase.mrs.memberservice.repository.UserRepository;
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
public class SignupServiceImplTest {

    @InjectMocks
    SignupServiceImpl signupService;

    @Mock
    UserRepository userRepository;

    @Mock
    RatingRepository ratingRepository;

    /**
     * Test checkUsernameAvailable
     */
    @Test
    public void checkUsernameAvailable() {
        User user = User.builder().id(1L).password("1234").username("admin").build();
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        when(userRepository.findByUsername("john")).thenReturn(Optional.empty());
        Assertions.assertFalse(signupService.checkUsernameAvailable("admin"));
        Assertions.assertTrue(signupService.checkUsernameAvailable("john"));
    }


    /**
     * Test checkUsernameAvailable with not available username
     */
    @Test
    public void checkCreateUserWithUsernameNotAvailable() {
        User user = User.builder().id(1L).password("1234").username("admin").build();
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        UserDto signupUser = UserDto.builder().username("admin").password("abcd").build();
        Assertions.assertThrows(GeneralWebserviceException.class, () -> {
            signupService.createUser(signupUser);
        });
    }

    /**
     * Test creating User
     */
    @Test
    public void checkCreateUserWithUsernameAvailable() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        UserDto signupUser = UserDto.builder().username("john").password("abcd").build();
        User dbUser0 = User.builder().password(encoder.encode("abcd")).username("john").tblRatingUserId(10L).build();
        when(userRepository.findByUsername("john")).thenReturn(Optional.empty());
        when(userRepository.findByTblRatingUserId(10L)).thenReturn(Optional.of(dbUser0));
        when(ratingRepository.getHighestId()).thenReturn(9L);

        User dbUser = User.builder().password(encoder.encode("abcd")).username("john").tblRatingUserId(11L).build();
        when(userRepository.save(any(User.class))).thenReturn(dbUser);
        User createdUser = signupService.createUser(signupUser);
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(argumentCaptor.capture());
        User capturedArgument = argumentCaptor.<User> getValue();
        Assertions.assertTrue(encoder.matches("abcd", capturedArgument.getPassword()));
        Assertions.assertEquals(signupUser.getUsername(), createdUser.getUsername());
        Assertions.assertEquals(11L, capturedArgument.getTblRatingUserId());
        Assertions.assertTrue(encoder.matches("abcd", createdUser.getPassword()));
    }

}
