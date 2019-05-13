import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from "rxjs";
import {User} from "../../shared/domain/domain";

@Injectable({
  providedIn: 'root'
})
export class ReportsService {

  private url = 'http://localhost:8080/reports';

  constructor(private http: HttpClient) {
  }

  public getMostActiveCustomer(): Observable<User> {
    return this.http.get<User>(`${this.url}/mostActive`);
  }

  public getBiggestSpender(): Observable<User> {
    return this.http.get<User>(`${this.url}/biggestSpender`);
  }

}
