import { Team } from './team';

export class Match {
  homeTeam: Team;
  homeTeamOdds: number;
  awayTeam: Team;
  awayTeamOdds: number;

  constructor(
    homeTeam: Team,
    homeTeamOdds: number,
    awayTeam: Team,
    awayTeamOdds: number
  ) {
    this.homeTeam = homeTeam;
    this.homeTeamOdds = homeTeamOdds;
    this.awayTeam = awayTeam;
    this.awayTeamOdds = awayTeamOdds;
  }
}
