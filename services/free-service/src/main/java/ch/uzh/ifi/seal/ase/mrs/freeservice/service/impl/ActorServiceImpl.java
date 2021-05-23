package ch.uzh.ifi.seal.ase.mrs.freeservice.service.impl;

import ch.uzh.ifi.seal.ase.mrs.freeservice.client.TmdbClient;
import ch.uzh.ifi.seal.ase.mrs.freeservice.exception.GeneralWebserviceException;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.Actor;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb.TmdbActor;
import ch.uzh.ifi.seal.ase.mrs.freeservice.repository.ActorRepository;
import ch.uzh.ifi.seal.ase.mrs.freeservice.service.IActorService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Implementation of IMovieService
 */
@Service
@Slf4j
@CacheConfig(cacheNames={"actors"})
public class ActorServiceImpl implements IActorService {
    private final ActorRepository actorRepository;
    private final TmdbClient tmdbClient;
    private final Random random;
    private final long numberOfActorsInDatabase;

    @Value("${tmdb.api-key}")
    private String tmdbApiKey;

    /**
     * Constructor, autowires the ActorRepository and the TmdbClient, creates a Random Object, and counts the number of actors in the database
     *
     * @param actorRepository actor repository
     * @param tmdbClient tmdbClient
     */
    @Autowired
    public ActorServiceImpl(ActorRepository actorRepository, TmdbClient tmdbClient) {
        this.actorRepository = actorRepository;
        this.tmdbClient = tmdbClient;
        this.random = new Random();
        this.numberOfActorsInDatabase = this.actorRepository.count();
    }

    /**
     * Collects actors until there are the amount of actors asked in the set
     *
     * @param amount amount of actors
     * @param popularity popularity of actors
     * @return set of actors as list
     */
    @Override
    public List<TmdbActor> getActors(Integer amount, Integer popularity) {
        if (amount > numberOfActorsInDatabase || amount > 10) {
            throw GeneralWebserviceException.builder().status(HttpStatus.BAD_REQUEST).errorCode("A001").message("Maximum number of actors per request: 10").build();
        }

        Set<TmdbActor> actors = new HashSet<>();
        int maxAttempts = amount * 10;
        int currentIter = 0;
        while (actors.size() < amount) {
            currentIter++;
            if(currentIter >= maxAttempts) {
                throw GeneralWebserviceException.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).errorCode("A002").message("Maximum number of retries reached without finding enough unique actors").build();
            }
            double lamda = (1 / (double) this.numberOfActorsInDatabase) * (50 * popularity);
            Optional<Actor> actor = actorRepository.findById(getRandomActorId(lamda));
            if(actor.isPresent()){
                try {
                    TmdbActor tmdbActor = getTmdbActor(actor.get().getTmdbId().toString());
                    if (tmdbActor != null && tmdbActor.getProfilePath() != null) {
                        actors.add(tmdbActor);
                    }
                }catch (FeignException exception) {
                    log.info("Problem with Feign Client or the API, e.g. Actor Not found. Retrying with another actor.");
                }

            }
        }

        return new ArrayList<>(actors);
    }

    /**
     * Wrapper method since feign clients cannot be testet with Mockito when
     */
    @Cacheable
    public TmdbActor getTmdbActor(String actorId) {
        return this.tmdbClient.getActor(actorId, tmdbApiKey);
    }

    /**
     * generates a random actor id based on negative exponential distribution
     *
     * @return actor Id
     */
    public long getRandomActorId(double lambda) {
        double l = 1;
        long movieId = (long) (-Math.log(Math.exp(-lambda * l) - (Math.exp(-lambda * l) - Math.exp(-lambda * this.numberOfActorsInDatabase)) * this.random.nextDouble()) / lambda);
        if (movieId >= 1 && movieId <= this.numberOfActorsInDatabase) {
            return movieId;
        }
        // return 1 if the generated number is not in the range due to some conversion errors, should not happen at all
        return 1;
    }
}
