import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Bet } from '../Bet';
import { ActivatedRoute } from '@angular/router';
import { BetType } from '../../match/BetType';

@Component({
  selector: 'betapp-user-bets',
  templateUrl: './user-bets.component.html',
  styleUrls: ['./user-bets.component.css'],
})
export class UserBetsComponent implements OnInit {
  matchId: number | null = null;
  bets: Bet[] = [];
  fullTimeResultBets: Bet[] = [];
  correctScoreBets: Bet[] = [];

  constructor(private http: HttpClient, private route: ActivatedRoute) {}
  ngOnInit() {
    const idString = this.route.snapshot.paramMap.get('id');
    if (idString) {
      this.matchId = +idString;
    }
    if (this.matchId) {
      this.getUserBets(this.matchId).subscribe((bets: Bet[]): void => {
        this.fullTimeResultBets = bets.filter(
          (bet: Bet): boolean => bet.type === BetType.FULL_TIME_RESULT
        );

        this.correctScoreBets = bets.filter(
          (bet: Bet): boolean => bet.type === BetType.CORRECT_SCORE
        );
      });
    }
  }

  private getUserBets(matchId: number) {
    return this.http.get<Bet[]>(`http://localhost:8080/bets/user/${matchId}`);
  }
}
