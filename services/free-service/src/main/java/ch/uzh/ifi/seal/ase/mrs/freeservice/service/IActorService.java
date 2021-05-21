package ch.uzh.ifi.seal.ase.mrs.freeservice.service;

import ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb.TmdbActor;

import java.util.List;

/**
 * Interface of Actor Service
 */
public interface IActorService {
    /**
     * Collects actors until there are the amount of actors asked in the set
     * @param amount amount of movies
     * @param popularity popularity of movies
     * @return set of actors as list
     */
    List<TmdbActor> getActors(Integer amount, Integer popularity);

}
