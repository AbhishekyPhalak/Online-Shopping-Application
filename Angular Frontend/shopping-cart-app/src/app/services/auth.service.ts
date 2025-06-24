import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private authUrl = 'http://localhost:9999'; // Auth endpoints (login/register)

  constructor(private http: HttpClient) {}

  login(credentials: { username: string; password: string }): Observable<any> {
    return this.http.post(`${this.authUrl}/login`, credentials);
  }

  register(data: { username: string; email: string; password: string }): Observable<any> {
    return this.http.post(`${this.authUrl}/signup`, data);
  }
}
