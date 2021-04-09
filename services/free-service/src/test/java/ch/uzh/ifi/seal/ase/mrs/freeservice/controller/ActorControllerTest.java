package ch.uzh.ifi.seal.ase.mrs.freeservice.controller;

import ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb.TmdbActor;
import ch.uzh.ifi.seal.ase.mrs.freeservice.service.IActorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Test ActorController
 */
@ExtendWith(MockitoExtension.class)
public class ActorControllerTest {

    @InjectMocks
    ActorController actorController;

    @Mock
    IActorService actorService;

    /**
     * Test get Actors
     */
    @Test
    public void testGetActors() {
        TmdbActor actor1 = TmdbActor.builder().id(1L).name("actor1").build();
        TmdbActor actor2 = TmdbActor.builder().id(2L).name("actor2").build();
        List<TmdbActor> actors = Arrays.asList(actor1, actor2);
        when(actorService.getActors(2)).thenReturn(actors);
        List<TmdbActor> result = actorController.getActors(2);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("actor1", result.get(0).getName());
    }
}
