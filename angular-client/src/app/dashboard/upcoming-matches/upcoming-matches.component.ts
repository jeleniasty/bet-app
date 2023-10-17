import { Component } from '@angular/core';
import { Match } from './upcoming-match/match';
import { Team } from './upcoming-match/team';

@Component({
  selector: 'betapp-upcoming-matches',
  templateUrl: './upcoming-matches.component.html',
  styleUrls: ['./upcoming-matches.component.css'],
})
export class UpcomingMatchesComponent {
  matches: Match[] = [
    new Match(
      new Team('Poland', 'https://flagcdn.com/w160/pl.png'),
      1.23,
      new Team('Moldova', 'https://flagcdn.com/w160/md.png'),
      2.54
    ),
    new Match(
      new Team('Spain', 'https://flagcdn.com/w160/es.png'),
      1.11,
      new Team('Germany', 'https://flagcdn.com/w160/de.png'),
      1.34
    ),
    new Match(
      new Team('Croatia', 'https://flagcdn.com/w320/hr.png'),
      1.64,
      new Team('France', 'https://flagcdn.com/w320/fr.png'),
      1.11
    ),
    new Match(
      new Team('Albania', 'https://flagcdn.com/w320/al.png'),
      1.23,
      new Team('Czech Republic', 'https://flagcdn.com/w320/cz.png'),
      2.54
    ),
  ];
}
