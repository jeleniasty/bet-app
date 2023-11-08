import { Component, Input, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BetType } from '../../match/BetType';
import { Duration } from '../../match/Duration';
import { Bet } from '../Bet';

@Component({
  selector: 'betapp-user-bets',
  templateUrl: './user-bets.component.html',
  styleUrls: ['./user-bets.component.css'],
})
export class UserBetsComponent implements OnInit {
  @Input() matchId: number | undefined;
  bets: Bet[] = [];

  constructor(private http: HttpClient) {}
  ngOnInit() {
    this.bets = [
      new Bet(3, BetType.CORRECT_SCORE, 4, 5, new Date()),
      new Bet(
        2,
        BetType.FULL_TIME_RESULT,
        5,
        1,
        new Date(),
        Duration.EXTRA_TIME
      ),
      new Bet(1, BetType.CORRECT_SCORE, 2, 2, new Date()),
      new Bet(
        4,
        BetType.FULL_TIME_RESULT,
        1,
        1,
        new Date(),
        Duration.REGULAR_TIME
      ),
    ];
  }
}
