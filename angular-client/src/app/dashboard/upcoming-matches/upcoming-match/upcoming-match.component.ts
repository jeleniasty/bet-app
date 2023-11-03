import { Component, Input } from '@angular/core';
import { UpcomingMatch } from './UpcomingMatch';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'betapp-upcoming-match',
  templateUrl: './upcoming-match.component.html',
  styleUrls: ['./upcoming-match.component.css'],
})
export class UpcomingMatchComponent {
  @Input() upcomingMatch: UpcomingMatch | undefined;

  constructor(private datePipe: DatePipe) {
    if (this.upcomingMatch)
      this.upcomingMatch.matchDate = this.transformDate(
        this.upcomingMatch.matchDate.toString()
      );
  }
  private transformDate(date: string): Date {
    return new Date(this.datePipe.transform(date, 'dd/MM/yyyy HH:mm') || '');
  }

  getMatchDay(): string | undefined {
    return this.upcomingMatch?.matchDate.toString().slice(0, 10);
  }

  getMatchHour(): string | undefined {
    return this.upcomingMatch?.matchDate.toString().slice(11, 16);
  }
}
