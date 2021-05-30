package ch.uzh.ifi.seal.ase.mrs.memberservice.controller;

import ch.uzh.ifi.seal.ase.mrs.memberservice.model.dto.ActorRatingDto;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.dto.MovieRatingDto;
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
import static org.mockito.Mockito.when;

/**
 * Test MemberController
 */
@ExtendWith(MockitoExtension.class)
public class MemberControllerTest {
    @InjectMocks
    MemberController memberController;

    @Mock
    MemberServiceImpl memberService;

    @Mock
    Principal principal;


    /**
     * Test rate movie
     */
    @Test
    public void testNewMovieRating() {

        MovieRatingDto movieRatingDto = MovieRatingDto.builder().tmdbId(1L).rating(4.0).build();
        when(principal.getName()).thenReturn("admin");
        memberController.newMovieRating(movieRatingDto, principal);
        ArgumentCaptor<MovieRatingDto> argumentCaptor1 = ArgumentCaptor.forClass(MovieRatingDto.class);
        ArgumentCaptor<Principal> argumentCaptor2 = ArgumentCaptor.forClass(Principal.class);
        verify(memberService).rateMovie(argumentCaptor1.capture(), argumentCaptor2.capture());
        MovieRatingDto capturedArgument1 = argumentCaptor1.<User> getValue();
        Assertions.assertEquals(4.0, capturedArgument1.getRating());
        Assertions.assertEquals(1L, capturedArgument1.getTmdbId());

        Principal capturedArgument2 = argumentCaptor2.<User> getValue();
        Assertions.assertEquals("admin", capturedArgument2.getName());
    }

    /**
     * Test rate actor
     */
    @Test
    public void testNewActorRating() {

        ActorRatingDto actorRatingDto = ActorRatingDto.builder().tmdbId(1L).rating(4.0).build();
        when(principal.getName()).thenReturn("admin");
        memberController.newActorRating(actorRatingDto, principal);
        ArgumentCaptor<ActorRatingDto> argumentCaptor1 = ArgumentCaptor.forClass(ActorRatingDto.class);
        ArgumentCaptor<Principal> argumentCaptor2 = ArgumentCaptor.forClass(Principal.class);
        verify(memberService).rateActor(argumentCaptor1.capture(), argumentCaptor2.capture());
        ActorRatingDto capturedArgument1 = argumentCaptor1.<User> getValue();
        Assertions.assertEquals(4.0, capturedArgument1.getRating());
        Assertions.assertEquals(1L, capturedArgument1.getTmdbId());

        Principal capturedArgument2 = argumentCaptor2.<User> getValue();
        Assertions.assertEquals("admin", capturedArgument2.getName());
    }
}
