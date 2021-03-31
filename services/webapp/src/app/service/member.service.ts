import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {UserDto} from '../model/userDto';

@Injectable({
  providedIn: 'root'
})
export class MemberService {
  private serviceUrl = environment.memberBackend;

  constructor(private httpClient: HttpClient) {
  }

  public checkUsernameAvailable(username: string): Observable<boolean> {
    return this.httpClient.get<boolean>(this.serviceUrl + '/api/signup/checkUsernameAvailable/' + username);
  }

  public createUser(user: UserDto): Observable<UserDto> {
    return this.httpClient.post<UserDto>(this.serviceUrl + '/api/signup/', user);
  }
}
