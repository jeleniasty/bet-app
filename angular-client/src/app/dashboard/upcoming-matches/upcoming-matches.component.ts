import { Component, Input, OnInit } from '@angular/core';
import { UpcomingMatch } from './upcoming-match/UpcomingMatch';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'betapp-upcoming-matches',
  templateUrl: './upcoming-matches.component.html',
  styleUrls: ['./upcoming-matches.component.css'],
})
export class UpcomingMatchesComponent implements OnInit {
  constructor(private http: HttpClient, private router: Router) {}
  @Input() pageable: boolean = false;
  page: number = 0;
  matches: UpcomingMatch[] = [];

  ngOnInit(): void {
    if (!this.pageable) {
      this.getUpcomingMatches(0, 10).subscribe(
        (upcomingMatches: UpcomingMatch[]) => {
          this.matches = upcomingMatches;
        }
      );
    } else {
      this.getUpcomingMatches(this.page, 10).subscribe(
        (upcomingMatches: UpcomingMatch[]) => {
          this.matches = upcomingMatches;
        }
      );
    }
  }

  showNextPage(): void {
    if (this.pageable) {
      this.page++;
      this.getUpcomingMatches(this.page, 10).subscribe(
        (nextUpcomingMatches: UpcomingMatch[]) => {
          this.matches = this.matches.concat(nextUpcomingMatches);
        }
      );
    }
  }

  openBet(matchId: number): void {
    this.router.navigateByUrl(`/match/${matchId}`);
  }

  getUpcomingMatches(page: number, size: number): Observable<UpcomingMatch[]> {
    return this.http.get<UpcomingMatch[]>(
      environment.backendUrl +
        '/matches/upcoming?page=' +
        page +
        '&size=' +
        size
    );
  }
}
