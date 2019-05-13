import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Log} from '../../shared/domain/domain';

@Injectable({
  providedIn: 'root'
})
export class LogsService {

  private url = 'http://localhost:8080/logs';

  constructor(private http: HttpClient) {
  }

  public findAll(): Observable<Log[]> {
    return this.http.get<Log[]>(this.url);
  }

  public save(Log: Log): Observable<void> {
    return this.http.post<void>(`${this.url}/save`, Log);
  }

  public update(Log: Log): Observable<Log> {
    return this.http.post<Log>(`${this.url}/update`, Log);
  }

  public get(id: string): Observable<Log> {
    return this.http.get<Log>(`${this.url}/${id}`);
  }

  public delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }

}
