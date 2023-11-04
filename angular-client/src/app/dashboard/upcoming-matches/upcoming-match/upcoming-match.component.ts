import { Component, Input } from '@angular/core';
import { UpcomingMatch } from './UpcomingMatch';

@Component({
  selector: 'betapp-upcoming-match',
  templateUrl: './upcoming-match.component.html',
  styleUrls: ['./upcoming-match.component.css'],
})
export class UpcomingMatchComponent {
  @Input() upcomingMatch: UpcomingMatch | undefined;

  constructor() {}
}
