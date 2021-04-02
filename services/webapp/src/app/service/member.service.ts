import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {UserDto} from '../model/userDto';
import {Router} from '@angular/router';

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

  public logout(): void {
    if (this.isLoggedIn){
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
}
