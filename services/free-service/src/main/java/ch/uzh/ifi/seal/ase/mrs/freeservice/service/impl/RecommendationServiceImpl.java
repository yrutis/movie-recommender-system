package ch.uzh.ifi.seal.ase.mrs.freeservice.service.impl;

import ch.uzh.ifi.seal.ase.mrs.freeservice.client.InferenceClient;

import ch.uzh.ifi.seal.ase.mrs.freeservice.model.ActorRating;

import ch.uzh.ifi.seal.ase.mrs.freeservice.model.MovieRating;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb.TmdbMovie;
import ch.uzh.ifi.seal.ase.mrs.freeservice.repository.MovieRepository;
import ch.uzh.ifi.seal.ase.mrs.freeservice.service.IRecommendationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Implementation of IRecommendationService
 */
@Service
@Slf4j
public class RecommendationServiceImpl implements IRecommendationService {
    private final MovieRepository movieRepository;
    private final InferenceClient inferenceClient;


    /**
     * Constructor, autowires the MovieRepository and the InferenceClient
     *
     * @param movieRepository movie repository
     * @param inferenceClient InferenceClient
     */
    @Autowired
    public RecommendationServiceImpl(MovieRepository movieRepository, InferenceClient inferenceClient) {
        this.movieRepository = movieRepository;
        this.inferenceClient = inferenceClient;

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
        // TODO: Translate ActorRatings in MovieRatings and send it to inference backend, translate it back to movie recommendations
        return new ArrayList<>();
    }
}
