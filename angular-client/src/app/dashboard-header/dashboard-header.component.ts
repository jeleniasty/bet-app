import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'betapp-dashboard-header',
  templateUrl: './dashboard-header.component.html',
  styleUrls: ['./dashboard-header.component.css'],
})
export class DashboardHeaderComponent {
  constructor(private router: Router) {}

  redirectToDashboard(): void {
    this.router.navigateByUrl('');
  }
}
