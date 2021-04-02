package ch.uzh.ifi.seal.ase.mrs.freeservice.controller;

import ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb.TmdbMovie;
import ch.uzh.ifi.seal.ase.mrs.freeservice.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest Controller which serves a certain amount of movies
 */
@RestController
@RequestMapping("api/movies")
@CrossOrigin(origins = "${HOSTS.GUI}")
public class MovieController {

    private final IMovieService movieService;

    /**
     * Constructor, autowires the movie service
     *
     * @param movieService movie service
     */
    @Autowired
    public MovieController(IMovieService movieService) {
        this.movieService = movieService;
    }


    /**
     * List of movies
     * @param amount the number of movies
     * @return List of movies
     */
    @GetMapping("/{amount}")
    public List<TmdbMovie> getMovies(@PathVariable("amount") Integer amount) {
        return movieService.getMovies(amount);
    }
}
