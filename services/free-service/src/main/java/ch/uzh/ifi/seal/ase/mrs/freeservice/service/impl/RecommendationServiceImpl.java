package ch.uzh.ifi.seal.ase.mrs.freeservice.service.impl;

import ch.uzh.ifi.seal.ase.mrs.freeservice.client.InferenceClient;

import ch.uzh.ifi.seal.ase.mrs.freeservice.model.Actor;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.ActorRating;

import ch.uzh.ifi.seal.ase.mrs.freeservice.model.MovieRating;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb.TmdbMovie;
import ch.uzh.ifi.seal.ase.mrs.freeservice.repository.ActorRepository;
import ch.uzh.ifi.seal.ase.mrs.freeservice.service.IMovieService;
import ch.uzh.ifi.seal.ase.mrs.freeservice.service.IRecommendationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of IRecommendationService
 */
@Service
@Slf4j
public class RecommendationServiceImpl implements IRecommendationService {
    private final ActorRepository actorRepository;
    private final InferenceClient inferenceClient;
    private final IMovieService movieService;


    /**
     * Constructor, autowires the ActorRepository and the InferenceClient
     *
     * @param actorRepository actorRepository
     * @param inferenceClient InferenceClient
     */
    @Autowired
    public RecommendationServiceImpl(ActorRepository actorRepository, InferenceClient inferenceClient, IMovieService movieService) {
        this.actorRepository = actorRepository;
        this.inferenceClient = inferenceClient;
        this.movieService = movieService;

    }

    /**
     * Gets recommendations from the inference backend
     *
     * @param movieRatings List of rated movies
     * @param actorRatings List of rated actors
     * @return List of movie recommendations
     */
    @Override
    public List<TmdbMovie> getRecommendations(List<MovieRating> movieRatings, List<ActorRating> actorRatings) {
        List<MovieRating> additionalRatings = new ArrayList<>();
        actorRatings.forEach(actorRating -> {
            Actor actor = actorRepository.findByTmdbId(actorRating.getTmdbId()).get();
            additionalRatings.add(MovieRating.builder().tmdbId(actor.getTop1Movie()).rating(actorRating.getRating()).build());
            additionalRatings.add(MovieRating.builder().tmdbId(actor.getTop2Movie()).rating(actorRating.getRating()).build());
            additionalRatings.add(MovieRating.builder().tmdbId(actor.getTop3Movie()).rating(actorRating.getRating()).build());
        });
        movieRatings.addAll(additionalRatings);
        List<Long> movieIds = inferenceClient.getRecommendations(movieRatings);
        Set<TmdbMovie> movies = movieIds.stream().map(movieService::getTmdbMovieById).collect(Collectors.toSet());
        movies.remove(null);
        return movies.stream().filter(tmdbMovie -> tmdbMovie.getPosterPath() != null).collect(Collectors.toList());
    }
}
