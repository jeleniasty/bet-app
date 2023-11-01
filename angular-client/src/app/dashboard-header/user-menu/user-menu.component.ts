import { Component } from '@angular/core';
import { AuthService } from '../../authentication/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'betapp-user-menu',
  templateUrl: './user-menu.component.html',
  styleUrls: ['./user-menu.component.css'],
})
export class UserMenuComponent {
  isDropdownExpanded: boolean = false;

  constructor(private authService: AuthService, private router: Router) {}

  toggleDropdown() {
    this.isDropdownExpanded = !this.isDropdownExpanded;
  }

  logout() {
    this.authService.removeAuthToken();
    this.toggleDropdown();
    this.router.navigateByUrl('/login');
  }
}
