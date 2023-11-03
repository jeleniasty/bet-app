import { Component, OnInit } from '@angular/core';
import { UpcomingMatch } from './upcoming-match/UpcomingMatch';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Component({
  selector: 'betapp-upcoming-matches',
  templateUrl: './upcoming-matches.component.html',
  styleUrls: ['./upcoming-matches.component.css'],
})
export class UpcomingMatchesComponent implements OnInit {
  constructor(private http: HttpClient) {}

  matches: UpcomingMatch[] = [];

  ngOnInit() {
    this.getUpcomingMatches().subscribe((upcomingMatches: UpcomingMatch[]) => {
      this.matches = upcomingMatches;
      console.log(upcomingMatches);
    });
  }

  getUpcomingMatches(): Observable<UpcomingMatch[]> {
    return this.http.get<UpcomingMatch[]>(
      'http://localhost:8080/matches/upcoming'
    );
  }
}
