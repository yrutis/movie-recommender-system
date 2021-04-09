package ch.uzh.ifi.seal.ase.mrs.freeservice.service;

import ch.uzh.ifi.seal.ase.mrs.freeservice.client.TmdbClient;
import ch.uzh.ifi.seal.ase.mrs.freeservice.exception.GeneralWebserviceException;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.Actor;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.Actor;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb.TmdbActor;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb.TmdbActor;
import ch.uzh.ifi.seal.ase.mrs.freeservice.repository.ActorRepository;
import ch.uzh.ifi.seal.ase.mrs.freeservice.repository.ActorRepository;
import ch.uzh.ifi.seal.ase.mrs.freeservice.service.impl.ActorServiceImpl;
import ch.uzh.ifi.seal.ase.mrs.freeservice.service.impl.ActorServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

/**
 * Test ActorServiceImplTest
 */
@ExtendWith(MockitoExtension.class)
public class ActorServiceImplTest {


    @Mock
    ActorRepository actorRepository;

    @Mock
    TmdbClient tmdbClient;


    /**
     * Test getActors
     */
    @Test
    public void testGetActors() {
        int amount = 30;
        Map<Long, TmdbActor> tmdbActorMap = new HashMap<>();
        for (int i = 1; i <= amount; i++) {
            Optional<Actor> actor = Optional.of(new Actor((long) i, ((long) i + 1), 5L, 6L, 7L, 5.0));
            lenient().when(actorRepository.findById((long) i)).thenReturn(actor);
            TmdbActor tmdbActor = TmdbActor.builder().id((long) i + 1).name("Title" + i+1).profilePath("Path").build();
            tmdbActorMap.put(tmdbActor.getId(), tmdbActor);
        }

        when(actorRepository.count()).thenReturn((long) amount);
        ActorServiceImpl actorService = new ActorServiceImpl(actorRepository, tmdbClient) {
            long i = 0;
            
            @Override
            public TmdbActor getTmdbActor(String actorId) {
                return tmdbActorMap.get(Long.parseLong(actorId));
            }
            // Override the random ID generator, in order to test the get actors method efficiently
            @Override
            public long getRandomActorId() {
                long result = (i % amount) + 1;
                i++;
                return result;
            }
        };
        final List<TmdbActor> result = actorService.getActors(3);

        Assertions.assertEquals(3, result.size());

        final List<TmdbActor> result2 = actorService.getActors(2);
        Assertions.assertEquals(2, result2.size());

        Assertions.assertThrows(GeneralWebserviceException.class, () -> {
            final List<TmdbActor> result3 = actorService.getActors(20);
        });
    }

    /**
     * Test getActors with amount smaller than 10 but larger than in db
     */
    @Test
    public void testGetActorsAmountSmallerThanNumberInDb() {
        int amount = 6;
        Map<Long, TmdbActor> tmdbActorMap = new HashMap<>();
        for (int i = 1; i <= amount; i++) {
            Optional<Actor> actor = Optional.of(new Actor((long) i, ((long) i + 1), 5L, 6L, 7L, 5.0));
            lenient().when(actorRepository.findById((long) i)).thenReturn(actor);
            TmdbActor tmdbActor = TmdbActor.builder().id((long) i + 1).name("Name" + i+1).profilePath("Path").build();
            tmdbActorMap.put(tmdbActor.getId(), tmdbActor);
        }

        when(actorRepository.count()).thenReturn((long) amount);
        ActorServiceImpl actorService = new ActorServiceImpl(actorRepository, tmdbClient) {
            long i = 0;
            @Override
            public TmdbActor getTmdbActor(String actorId) {
                return tmdbActorMap.get(Long.parseLong(actorId));
            }
            // Override the random ID generator, in order to test the get actors method efficiently
            @Override
            public long getRandomActorId() {
                long result = (i % amount) + 1;
                i++;
                return result;
            }
        };
        Assertions.assertThrows(GeneralWebserviceException.class, () -> {
            final List<TmdbActor> result = actorService.getActors(8);
        });
    }


    /**
     * Test getActors alternative paths
     */
    @Test
    public void testGetActorsBranches() {
        Map<Long, TmdbActor> tmdbActorMap = new HashMap<>();
        Optional<Actor> actor1 = Optional.of(new Actor((long) 1, ((long) 2), 5L, 6L, 7L, 5.0));
        Optional<Actor> actor3 = Optional.of(new Actor((long) 3, ((long) 4), 5L, 6L, 7L, 5.0));
        Optional<Actor> actor4 = Optional.of(new Actor((long) 4, ((long) 5), 5L, 6L, 7L, 5.0));
        TmdbActor tmdbActor1 = TmdbActor.builder().id((long) 2).name("Name 2").profilePath("Path").build();
        TmdbActor tmdbActor4 = TmdbActor.builder().id((long) 5).name("Name 5").profilePath("Path").build();
        when(actorRepository.findById((long) 1)).thenReturn(actor1);
        when(actorRepository.findById((long) 2)).thenReturn(Optional.empty());
        when(actorRepository.findById((long) 3)).thenReturn(actor3);
        when(actorRepository.findById((long) 4)).thenReturn(actor4);
        tmdbActorMap.put(2L, tmdbActor1);
        tmdbActorMap.put(4L, null);
        tmdbActorMap.put(5L, tmdbActor4);
        when(actorRepository.count()).thenReturn((3L));

        ActorServiceImpl actorService = new ActorServiceImpl(actorRepository, tmdbClient) {
            long i = 1;
            @Override
            public TmdbActor getTmdbActor(String actorId) {
                return tmdbActorMap.get(Long.parseLong(actorId));
            }
            // Override the random ID generator, in order to test the get actors method efficiently
            @Override
            public long getRandomActorId() {
                long result = i;
                i++;
                return result;
            }
        };
        final List<TmdbActor> result = actorService.getActors(2);

        Assertions.assertEquals(2, result.size());
        Assertions.assertThrows(GeneralWebserviceException.class, () -> {
            actorService.getActors(9);
        });

    }

    /**
     * Test if random Id is within range
     */
    @Test
    public void testGetRandomActorId() {
        when(actorRepository.count()).thenReturn((5L));

        ActorServiceImpl actorService = new ActorServiceImpl(actorRepository, tmdbClient);

        for (int i = 0; i < 50; i++) {
            long randomId = actorService.getRandomActorId();
            Assertions.assertTrue(randomId >= 1 && randomId <= 5);
        }

    }
}
