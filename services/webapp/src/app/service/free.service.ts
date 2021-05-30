import { Injectable } from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Movie} from '../model/movie';
import {RatingDto} from '../model/ratings';
import {Actor} from '../model/actor';

/**
 * REST Client that connects to the free-service
 */
@Injectable({
  providedIn: 'root'
})
export class FreeService {

  private serviceUrl = environment.freeBackend;

  constructor(private httpClient: HttpClient) {
  }

  /**
   * Get movies
   * @param amount The amount of movies to load
   * @param moviePopularity The popularity (1-10) of the movies to load
   */
  public getMovies(amount: number, moviePopularity: number): Observable<Movie[]> {
    return this.httpClient.get<Movie[]>(this.serviceUrl + '/api/movies/' + amount + '/' + moviePopularity);
  }

  /**
   * Get actors
   * @param amount The amount of actors to load
   * @param actorPopularity The popularity (1-10) of the actors to load
   */
  public getActors(amount: number, actorPopularity: number): Observable<Actor[]> {
    return this.httpClient.get<Actor[]>(this.serviceUrl + '/api/actors/' + amount + '/' + actorPopularity);
  }

  /**
   * Get free recommendations
   * @param ratingDto Wrapper that contains movie and actor ratings
   */
  public getRecommendations(ratingDto: RatingDto): Observable<Movie[]> {
    return this.httpClient.post<Movie[]>(this.serviceUrl + '/api/recommendations/', ratingDto);
  }
}
