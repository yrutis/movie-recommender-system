import { Injectable } from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Movie} from '../model/movie';
import {RatingDto} from '../model/ratings';
import {Actor} from '../model/actor';

@Injectable({
  providedIn: 'root'
})
export class FreeService {

  private serviceUrl = environment.freeBackend;

  constructor(private httpClient: HttpClient) {
  }

  public getMovies(amount: number): Observable<Movie[]> {
    return this.httpClient.get<Movie[]>(this.serviceUrl + '/api/movies/' + amount);
  }

  public getActors(amount: number): Observable<Actor[]> {
    return this.httpClient.get<Actor[]>(this.serviceUrl + '/api/actors/' + amount);
  }

  public getRecommendations(ratingDto: RatingDto): Observable<Movie[]> {
    return this.httpClient.post<Movie[]>(this.serviceUrl + '/api/recommendations/', ratingDto);
  }
}
