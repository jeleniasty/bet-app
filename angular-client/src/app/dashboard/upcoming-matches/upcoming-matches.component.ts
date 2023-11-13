import { Component, OnInit } from '@angular/core';
import { UpcomingMatch } from './upcoming-match/UpcomingMatch';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'betapp-upcoming-matches',
  templateUrl: './upcoming-matches.component.html',
  styleUrls: ['./upcoming-matches.component.css'],
})
export class UpcomingMatchesComponent implements OnInit {
  constructor(private http: HttpClient, private router: Router) {}

  matches: UpcomingMatch[] = [];

  ngOnInit() {
    this.getUpcomingMatches().subscribe((upcomingMatches: UpcomingMatch[]) => {
      this.matches = upcomingMatches;
    });
  }

  openBet(matchId: number): void {
    this.router.navigateByUrl(`/match/${matchId}`);
  }

  getUpcomingMatches(): Observable<UpcomingMatch[]> {
    return this.http.get<UpcomingMatch[]>(
      'http://localhost:8080/matches/upcoming'
    );
  }
}
