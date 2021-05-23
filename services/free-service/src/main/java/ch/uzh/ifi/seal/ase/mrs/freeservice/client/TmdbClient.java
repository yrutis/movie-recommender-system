package ch.uzh.ifi.seal.ase.mrs.freeservice.client;

import ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb.TmdbActor;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb.TmdbCast;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb.TmdbMovie;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb.TmdbWatchProvider;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Feign Client for interacting with the TheMoviesDataBase API
 */
@FeignClient(url = "${tmdb.url}", name = "tmdb")
public interface TmdbClient {

    /**
     * Get details of a movie
     * @param movieId ID of the movie
     * @param apiKey API Key
     * @return Movie Details (TmdbMovie)
     */
    @GetMapping("/movie/{movieId}")
    TmdbMovie getMovie(@PathVariable("movieId") String movieId, @RequestParam("api_key") String apiKey);

    /**
     * Get details of an actor
     * @param actorId ID of the actor
     * @param apiKey API Key
     * @return Actor Details (TmdbActor)
     */
    @GetMapping("/person/{actorId}")
    TmdbActor getActor(@PathVariable("actorId") String actorId, @RequestParam("api_key") String apiKey);

    /**
     * Get cast of a movie
     * @param movieId ID of the movie
     * @param apiKey API Key
     * @return Cast (TmdbCast)
     */
    @GetMapping("movie/{movieId}/credits")
    TmdbCast getCast(@PathVariable("movieId") String movieId, @RequestParam("api_key") String apiKey);

    /**
     * Get watch providers of a movie
     * @param movieId ID of the movie
     * @param apiKey API Key
     * @return Cast (TmdbWatchProvider)
     */
    @GetMapping("movie/{movieId}/watch/providers")
    TmdbWatchProvider getWatchProvider(@PathVariable("movieId") String movieId, @RequestParam("api_key") String apiKey);
}
