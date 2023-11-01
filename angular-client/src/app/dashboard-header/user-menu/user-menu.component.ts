import { Component } from '@angular/core';

@Component({
  selector: 'betapp-user-menu',
  templateUrl: './user-menu.component.html',
  styleUrls: ['./user-menu.component.css'],
})
export class UserMenuComponent {
  isDropdownExpanded: boolean = false;

  toggleDropdown() {
    this.isDropdownExpanded = !this.isDropdownExpanded;
  }
}
