import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RestService {

  private apiUrl = environment.apiUrl;

  constructor(
    private http: HttpClient
  ) { }

  public getOne<T>(resourceUrl: string, resourceId: any): Observable<T> {
    return this.http.get<T>(this.apiUrl + resourceUrl + '/' + resourceId);
  }

  public getAll<T>(resourceUrl: string): Observable<T> {
    return this.http.get<T>(this.apiUrl + resourceUrl);
  }

  public post<T>(resourceUrl: string, payload: T): Observable<T> {
    return this.http.post<T>(this.apiUrl + resourceUrl, payload);
  }

  public delete<T>(resourceUrl: string, resourceId: any): Observable<T> {
    return this.http.delete<T>(this.apiUrl + resourceUrl + '/' + resourceId);
  }

  public put<T>(resourceUrl: string, payload: T): Observable<T> {
    return this.http.put<T>(this.apiUrl + resourceUrl, payload);
  }
}
