import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Bet } from '../Bet';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'betapp-user-bets',
  templateUrl: './user-bets.component.html',
  styleUrls: ['./user-bets.component.css'],
})
export class UserBetsComponent implements OnInit {
  matchId: number | null = null;
  bets: Bet[] = [];

  constructor(private http: HttpClient, private route: ActivatedRoute) {}
  ngOnInit() {
    const idString = this.route.snapshot.paramMap.get('id');
    if (idString) {
      this.matchId = +idString;
    }
    if (this.matchId) {
      this.getUserBets(this.matchId).subscribe(
        (bets: Bet[]) => (this.bets = bets)
      );
    }
  }

  private getUserBets(matchId: number) {
    return this.http.get<Bet[]>(`http://localhost:8080/bets/user/${matchId}`);
  }
}
