import { Component, OnInit } from '@angular/core';
import { Bet } from '../Bet';
import { ActivatedRoute } from '@angular/router';
import { BetType } from '../../match/BetType';
import { BetService } from '../bet.service';

@Component({
  selector: 'betapp-user-bets',
  templateUrl: './user-bets.component.html',
  styleUrls: ['./user-bets.component.css'],
})
export class UserBetsComponent implements OnInit {
  matchId: number | null = null;
  private fullTimeResultBets: Bet[] = [];
  private correctScoreBets: Bet[] = [];

  constructor(private betService: BetService, private route: ActivatedRoute) {}
  ngOnInit(): void {
    const idString: string | null = this.route.snapshot.paramMap.get('id');
    if (idString) {
      this.matchId = +idString;
    }
    if (this.matchId) {
      this.betService.getUserBets(this.matchId).subscribe((bets: Bet[]) => {
        this.setFullTimeResultBets(bets);
        this.setCorrectScoreBets(bets);
      });

      this.betService.onBetCreated().subscribe(() => {
        this.betService.getUserBets(this.matchId!).subscribe((bets: Bet[]) => {
          this.setFullTimeResultBets(bets);
          this.setCorrectScoreBets(bets);
        });
      });
    }
  }

  setFullTimeResultBets(bets: Bet[]): void {
    this.fullTimeResultBets = bets.filter(
      (bet: Bet): boolean => bet.type === BetType.FULL_TIME_RESULT
    );
  }

  setCorrectScoreBets(bets: Bet[]): void {
    this.correctScoreBets = bets.filter(
      (bet: Bet): boolean => bet.type === BetType.CORRECT_SCORE
    );
  }

  getFullTimeResultBets(): Bet[] {
    return this.fullTimeResultBets;
  }

  getCorrectScoreBets(): Bet[] {
    return this.correctScoreBets;
  }
}
