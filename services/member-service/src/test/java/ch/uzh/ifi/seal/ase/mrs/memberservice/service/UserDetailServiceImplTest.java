package ch.uzh.ifi.seal.ase.mrs.memberservice.service;

import ch.uzh.ifi.seal.ase.mrs.memberservice.model.User;
import ch.uzh.ifi.seal.ase.mrs.memberservice.repository.UserRepository;
import ch.uzh.ifi.seal.ase.mrs.memberservice.service.impl.UserDetailServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Test UserDetailServiceImpl
 */
@ExtendWith(MockitoExtension.class)
public class UserDetailServiceImplTest {

    @InjectMocks
    UserDetailServiceImpl userDetailService;

    @Mock
    UserRepository userRepository;

    /**
     * Test loadUserByUsername with a non-valid username
     */
    @Test
    public void loadUserByUsernameNotFound() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            userDetailService.loadUserByUsername("any");
        });
    }

    /**
     * Test loadUserByUsername
     */
    @Test
    public void loadUserByUsername() {
        User user = User.builder().id(1L).password("1234").username("admin").build();
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        UserDetails userDetails = userDetailService.loadUserByUsername("admin");
        Assertions.assertEquals("admin", userDetails.getUsername());
        Assertions.assertEquals("1234", userDetails.getPassword());
    }
}
