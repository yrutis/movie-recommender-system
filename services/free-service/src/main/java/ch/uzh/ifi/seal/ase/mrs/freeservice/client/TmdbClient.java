package ch.uzh.ifi.seal.ase.mrs.freeservice.client;

import ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb.TmdbMovie;
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
}
