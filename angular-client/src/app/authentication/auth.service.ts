import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) {}

  login(email: string, password: string) {
    const headers: HttpHeaders = new HttpHeaders().set(
      'Content-Type',
      'application/x-www-form-urlencoded'
    );
    const body = new URLSearchParams();
    body.set('email', email);
    body.set('password', password);

    return this.http.post('http://localhost:8080/api/login', body.toString(), {
      headers,
    });
  }

  register(username: string, email: string, password: string) {
    const headers: HttpHeaders = new HttpHeaders().set(
      'Content-Type',
      'application/x-www-form-urlencoded'
    );
    const body = new URLSearchParams();
    body.set('username', username);
    body.set('email', email);
    body.set('password', password);
    body.set('roleNames', 'PLAYER');

    return this.http.post(
      'http://localhost:8080/api/register',
      body.toString(),
      {
        headers,
      }
    );
  }
}
