import { Component, Input } from '@angular/core';
import { UserScore } from '../UserScore';

@Component({
  selector: 'betapp-user-score',
  templateUrl: './user-score.component.html',
  styleUrls: ['./user-score.component.css'],
})
export class UserScoreComponent {
  @Input() user: UserScore | undefined;
  @Input() userStanding: number = 1;
}
