import { Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  showHeader = true;

  constructor(private router: Router) {
    this.router.events
      .pipe(filter((e) => e instanceof NavigationEnd))
      .subscribe((event) => this.modifyHeader(event));
  }

  modifyHeader(location: any): void {
    console.log(location.url);
    this.showHeader = !(
      location.url === '/login' || location.url === '/register'
    );
  }
}
