import { Injectable } from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Movie} from '../model/movie';

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
}
