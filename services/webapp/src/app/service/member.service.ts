import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {UserDto} from '../model/userDto';
import {Router} from '@angular/router';
import {ActorRating, MovieRating} from '../model/ratings';
import {Movie} from '../model/movie';

/**
 * REST Client that connects to the member-service
 */
@Injectable({
  providedIn: 'root'
})
export class MemberService {
  private serviceUrl = environment.memberBackend;
  private trainingAllowed = true;

  constructor(private httpClient: HttpClient, private router: Router) {
  }

  /**
   * Check if the username is still available
   * @param username username to check
   */
  public checkUsernameAvailable(username: string): Observable<boolean> {
    return this.httpClient.get<boolean>(this.serviceUrl + '/api/signup/checkUsernameAvailable/' + username);
  }

  /**
   * create a new user
   * @param user user object to be crated
   */
  public createUser(user: UserDto): Observable<UserDto> {
    return this.httpClient.post<UserDto>(this.serviceUrl + '/api/signup/', user);
  }

  /**
   * login request
   * @param user user dto containing login information
   */
  public login(user: UserDto): Observable<UserDto> {
    return this.httpClient.post<UserDto>(this.serviceUrl + '/api/login/', user);
  }

  /**
   * rate a single movie
   * @param movieRating movie rating object
   */
  public rateMovie(movieRating: MovieRating): Observable<void> {
    return this.httpClient.post<void>(this.serviceUrl + '/api/members/movieRating', movieRating, this.basicAuthHeader);
  }

  /**
   * rate a single actor
   * @param actorRating actor rating object
   */
  public rateActor(actorRating: ActorRating): Observable<void> {
    return this.httpClient.post<void>(this.serviceUrl + '/api/members/actorRating', actorRating, this.basicAuthHeader);
  }

  /**
   * get the user specific recommendations
   */
  public getRecommendations(): Observable<Movie[]> {
    return this.httpClient.get<Movie[]>(this.serviceUrl + '/api/recommendations', this.basicAuthHeader);
  }

  /**
   * get the date of last training
   */
  public getLastTrained(): Observable<Date> {
    return this.httpClient.get<Date>(this.serviceUrl + '/api/recommendations/lastTrained', this.basicAuthHeader);
  }

  /**
   * start the training manually for demo purposes
   */
  public startTrainingManually(): Observable<Date> {
    this.trainingAllowed = false;
    return this.httpClient.get<Date>(this.serviceUrl + '/api/recommendations/train', this.basicAuthHeader);
  }

  /**
   * logout a user
   */
  public logout(): void {
    if (this.isLoggedIn) {
      sessionStorage.removeItem(btoa('user_creds'));
      this.router.navigate(['/']);
    }
  }

  /**
   * set the logged in user
   * @param user user DTO
   */
  set loggedInUser(user: UserDto) {
    sessionStorage.setItem(btoa('user_creds'), btoa(JSON.stringify(user)));
  }

  /**
   * get the logged in user
   */
  get loggedInUser(): UserDto {
    if (this.isLoggedIn) {
      const user: UserDto = JSON.parse(atob(sessionStorage.getItem(btoa('user_creds'))));
      return user;
    }
    return null;
  }

  /**
   * check if user is logged in
   */
  get isLoggedIn(): boolean {
    return sessionStorage.getItem(btoa('user_creds')) ? true : false;
  }

  /**
   * generate a basic auth header that can be used for all requests requiring authentication
   */
  get basicAuthHeader(): { headers: HttpHeaders } {
    const header = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Authorization', 'Basic ' + btoa(this.loggedInUser.username + ':' + this.loggedInUser.password));
    return {
      headers: header
    };
  }

  /**
   * check if manual training is allowed
   */
  get isTrainingAllowed(): boolean {
    return this.trainingAllowed;
  }
}
