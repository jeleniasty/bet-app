import { Component } from '@angular/core';
import { User } from './user';

@Component({
  selector: 'betapp-scoreboard',
  templateUrl: './scoreboard.component.html',
  styleUrls: ['./scoreboard.component.css'],
})
export class ScoreboardComponent {
  users: User[] = [
    new User('kryspin91', 23.11, 4, 2, '../../assets/img/user_icon.png'),
    new User('norbasek', 11.53, 3, 4, '../../assets/img/user_icon.png'),
    new User('adrianek', 0.0, 0, 10, '../../assets/img/user_icon.png'),
    new User('radzio', 12.84, 2, 6, '../../assets/img/user_icon.png'),
    new User('jelen', 7.23, 2, 6, '../../assets/img/user_icon.png'),
  ];
}
