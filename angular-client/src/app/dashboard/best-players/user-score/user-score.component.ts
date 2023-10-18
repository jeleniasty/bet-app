import { Component, Input } from '@angular/core';
import { User } from '../user';

@Component({
  selector: 'betapp-user-score',
  templateUrl: './user-score.component.html',
  styleUrls: ['./user-score.component.css'],
})
export class UserScoreComponent {
  @Input() user: User | undefined;
  @Input() userStanding: number = 1;
}
