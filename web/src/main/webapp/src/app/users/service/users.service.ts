import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../../shared/domain/domain';


@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private url = 'http://localhost:8080/users/';

  constructor(private http: HttpClient) {
  }

  public findAll(): Observable<User[]> {
    return this.http.get<User[]>(this.url);
  }

  public save(user: User): Observable<void> {
    return this.http.post<void>(`${this.url}save`, user);
  }

  public update(user: User): Observable<User> {
    return this.http.post<User>(`${this.url}update`, user);
  }

  public get(id: string): Observable<User> {
    return this.http.get<User>(`${this.url}/${id}`);
  }

  public delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }

}
