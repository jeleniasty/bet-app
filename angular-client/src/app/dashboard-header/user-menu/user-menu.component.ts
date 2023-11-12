import { Component } from '@angular/core';
import { AuthService } from '../../authentication/auth.service';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';

@Component({
  selector: 'betapp-user-menu',
  templateUrl: './user-menu.component.html',
  styleUrls: ['./user-menu.component.css'],
})
export class UserMenuComponent {
  isDropdownExpanded: boolean = false;

  constructor(private authService: AuthService, private router: Router) {}

  toggleDropdown(): void {
    this.isDropdownExpanded = !this.isDropdownExpanded;
  }

  getUsernameFromJWT(): string | void {
    try {
      const jwtToken: string | void = this.authService.getAuthToken();
      if (!jwtToken) {
        return;
      }
      const decodedToken: any = jwtDecode(jwtToken);
      return decodedToken.username;
    } catch (error) {
      console.error('Error decoding JWT:', error);
    }
  }

  logout() {
    this.authService.removeAuthToken();
    this.toggleDropdown();
    this.router.navigateByUrl('/login');
  }
}
