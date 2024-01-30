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
  groupedMatches: Map<number, UpcomingMatch[]> = new Map();

  ngOnInit(): void {
    if (!this.pageable) {
      this.getUpcomingMatches(0, 10).subscribe(
        (upcomingMatches: UpcomingMatch[]): void => {
          this.updateGroupedMatchesByDate(upcomingMatches);
          console.log(this.groupedMatches.size);
        }
      );
    } else {
      this.getUpcomingMatches(this.page, 10).subscribe(
        (upcomingMatches: UpcomingMatch[]): void => {
          this.updateGroupedMatchesByDate(upcomingMatches);
        }
      );
    }
  }

  showNextPage(): void {
    if (this.pageable) {
      this.page++;
      this.getUpcomingMatches(this.page, 10).subscribe(
        (nextUpcomingMatches: UpcomingMatch[]): void => {
          this.updateGroupedMatchesByDate(nextUpcomingMatches);
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

  updateGroupedMatchesByDate(matches: UpcomingMatch[]): void {
    matches.forEach((match: UpcomingMatch): void => {
      const matchDateTime: number = this.formatDate(match.matchDate);
      if (this.groupedMatches.has(matchDateTime)) {
        let upcomingMatches: UpcomingMatch[] =
          this.groupedMatches.get(matchDateTime) ?? [];

        this.groupedMatches.set(matchDateTime, upcomingMatches?.concat(match));
      } else {
        this.groupedMatches.set(matchDateTime, [match]);
      }
    });
    console.log(this.groupedMatches);
  }

  private formatDate(date: Date): number {
    const trimmedDate: Date = new Date(date);
    trimmedDate.setHours(0, 0, 0, 0);

    return trimmedDate.getTime();
  }
}
