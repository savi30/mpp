import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Book} from "../../shared/domain/domain";

@Injectable({
  providedIn: 'root'
})
export class BookService {

  private url = 'http://localhost:8080/books';

  constructor(private http: HttpClient) {
  }

  public findAll(): Observable<Book[]> {
    return this.http.get<Book[]>(this.url);
  }

  public save(Book: Book): Observable<void> {
    return this.http.post<void>(`${this.url}/save`, Book);
  }

  public update(Book: Book): Observable<Book> {
    return this.http.post<Book>(`${this.url}/update`, Book);
  }

  public get(id: string): Observable<Book> {
    return this.http.get<Book>(`${this.url}/${id}`);
  }

  public delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
