package ch.uzh.ifi.seal.ase.mrs.freeservice.client;

import ch.uzh.ifi.seal.ase.mrs.freeservice.model.MovieRating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Feign Client to interact with the inference backend
 */
@FeignClient(url = "${inference.url}", name = "inference")
public interface InferenceClient {

    /**
     * Gets movie recommendations from the inference backend
     * @param movieRatings List of movie ratings
     * @return List of recommended movie IDs
     */
    @PostMapping("/tbd")
    List<Long> getRecommendations(@RequestBody List<MovieRating> movieRatings);
}
