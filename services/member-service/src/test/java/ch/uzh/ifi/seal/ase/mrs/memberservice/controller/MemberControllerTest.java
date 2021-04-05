package ch.uzh.ifi.seal.ase.mrs.memberservice.controller;

import ch.uzh.ifi.seal.ase.mrs.memberservice.model.MovieRating;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.User;
import ch.uzh.ifi.seal.ase.mrs.memberservice.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;

import static org.mockito.Mockito.verify;

/**
 * Test MemberController
 */
@ExtendWith(MockitoExtension.class)
public class MemberControllerTest {
    @InjectMocks
    MemberController memberController;

    @Mock
    MemberServiceImpl memberService;

    /**
     * Test rate movie
     */
    @Test
    public void testNewMovieRating() {

        MovieRating movieRating = MovieRating.builder().tmdbId(1L).rating(4.0).build();
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "Admin";
            }
        };
        memberController.newMovieRating(movieRating, principal);
        ArgumentCaptor<MovieRating> argumentCaptor1 = ArgumentCaptor.forClass(MovieRating.class);
        ArgumentCaptor<Principal> argumentCaptor2 = ArgumentCaptor.forClass(Principal.class);
        verify(memberService).rateMovie(argumentCaptor1.capture(), argumentCaptor2.capture());
        MovieRating capturedArgument1 = argumentCaptor1.<User> getValue();
        Assertions.assertEquals(4.0, capturedArgument1.getRating());
        Assertions.assertEquals(1L, capturedArgument1.getTmdbId());

        Principal capturedArgument2 = argumentCaptor2.<User> getValue();
        Assertions.assertEquals("Admin", capturedArgument2.getName());
    }
}
