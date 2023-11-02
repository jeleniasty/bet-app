import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  showHeader: boolean = true;

  ngOnInit() {
    this.showHeader = !(
      window.location.href.includes('/login') ||
      window.location.href.includes('/register')
    );
  }
}
