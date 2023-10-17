import { Component, Input } from '@angular/core';
import { Match } from './match';

@Component({
  selector: 'betapp-upcoming-match',
  templateUrl: './upcoming-match.component.html',
  styleUrls: ['./upcoming-match.component.css'],
})
export class UpcomingMatchComponent {
  @Input() upcomingMatch: Match | undefined;
}
