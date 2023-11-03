export class UpcomingMatch {
  homeTeam: string;
  homeFlag: string;
  homeOdds: number;
  awayTeam: string;
  awayFlag: string;
  awayOdds: number;
  matchDate: Date;

  constructor(
    homeTeamName: string,
    homeTeamFlag: string,
    homeTeamOdds: number,
    awayTeamName: string,
    awayTeamFlag: string,
    awayTeamOdds: number,
    matchDate: Date
  ) {
    this.homeTeam = homeTeamName;
    this.homeFlag = homeTeamFlag;
    this.homeOdds = homeTeamOdds;
    this.awayTeam = awayTeamName;
    this.awayFlag = awayTeamFlag;
    this.awayOdds = awayTeamOdds;
    this.matchDate = matchDate;
  }
}
