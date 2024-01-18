import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'betapp-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css'],
})
export class ErrorComponent {
  @Input() errorMessage: string = 'An error occurred';
}
