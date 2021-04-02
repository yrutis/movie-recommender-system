package ch.uzh.ifi.seal.ase.mrs.freeservice.client;

import ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb.TmdbMovie;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(url = "https://api.themoviedb.org/3", name = "tmdb")
public interface TmdbClient {

    @GetMapping("/movie/{movieId}")
    TmdbMovie getMovie(@PathVariable("movieId") String movieId, @RequestParam("api_key") String apiKey);
}
