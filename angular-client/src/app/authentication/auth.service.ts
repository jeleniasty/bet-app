import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { AuthenticationRequest } from './authentication-request';
import { RegistrationRequest } from './registration-request';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly authTokenName: string = 'authToken';

  constructor(private http: HttpClient, private cookieService: CookieService) {}

  login(email: string, password: string) {
    const authRequest: AuthenticationRequest = new AuthenticationRequest(
      email,
      password
    );

    return this.http.post('http://localhost:8080/api/login', authRequest);
  }

  register(username: string, email: string, password: string) {
    const registrationRequest: RegistrationRequest = new RegistrationRequest(
      username,
      email,
      password,
      ['PLAYER']
    );
    return this.http.post(
      'http://localhost:8080/api/register',
      registrationRequest
    );
  }

  storeAuthToken(token: string): void {
    if (this.cookieService) {
      this.cookieService.set(this.authTokenName, token);
    }
  }

  getAuthToken(): string | void {
    if (this.cookieService) {
      return this.cookieService.get(this.authTokenName);
    }
    return;
  }

  removeAuthToken(): void {
    if (this.cookieService) {
      this.cookieService.delete(this.authTokenName);
    }
  }
}
