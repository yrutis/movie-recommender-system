package ch.uzh.ifi.seal.ase.mrs.memberservice.client;


import ch.uzh.ifi.seal.ase.mrs.memberservice.model.tmdb.TmdbMovie;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
