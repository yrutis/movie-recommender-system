import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {UserDto} from '../model/userDto';
import {Router} from '@angular/router';
import {MovieRating} from '../model/ratings';

@Injectable({
  providedIn: 'root'
})
export class MemberService {
  private serviceUrl = environment.memberBackend;



  constructor(private httpClient: HttpClient, private router: Router) {
  }

  public checkUsernameAvailable(username: string): Observable<boolean> {
    return this.httpClient.get<boolean>(this.serviceUrl + '/api/signup/checkUsernameAvailable/' + username);
  }

  public createUser(user: UserDto): Observable<UserDto> {
    return this.httpClient.post<UserDto>(this.serviceUrl + '/api/signup/', user);
  }

  public login(user: UserDto): Observable<UserDto> {
    return this.httpClient.post<UserDto>(this.serviceUrl + '/api/login/', user);
  }

  public rateMovie(movieRating: MovieRating): Observable<void> {
    const httpHeaders = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Authorization', 'Basic ' + btoa(this.loggedInUser.username + ':' + this.loggedInUser.password));

    const httpOptions = {
      headers: httpHeaders
    };
    console.log(httpOptions);
    return this.httpClient.post<void>(this.serviceUrl + '/api/members/movieRating', movieRating, httpOptions);
  }

  public logout(): void {
    if (this.isLoggedIn) {
      sessionStorage.removeItem(btoa('user_creds'));
      this.router.navigate(['/']);
    }
  }

  set loggedInUser(user: UserDto) {
    sessionStorage.setItem(btoa('user_creds'), btoa(JSON.stringify(user)));
  }

  get loggedInUser(): UserDto {
    if (this.isLoggedIn) {
      const user: UserDto = JSON.parse(atob(sessionStorage.getItem(btoa('user_creds'))));
      return user;
    }
    return null;
  }

  get isLoggedIn(): boolean {
    return sessionStorage.getItem(btoa('user_creds')) ? true : false;
  }

  get basicAuthHeader(): { headers: HttpHeaders } {
    const header = new HttpHeaders();
    // header.append('Content-Type', 'application/json');
    header.append('Authorization', 'Basic ' + btoa(this.loggedInUser.username + ':' + this.loggedInUser.password));

    const httpOptions = {
      headers: header
    };
    return httpOptions;
  }
}
