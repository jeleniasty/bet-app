import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthGuardService {
  constructor(private authService: AuthService, private router: Router) {}

  //TODO add more secure frontend authentication
  canActivate(): boolean {
    if (this.authService.getAuthToken()) {
      return true;
    }

    this.router.navigateByUrl('/login');
    return false;
  }
}
