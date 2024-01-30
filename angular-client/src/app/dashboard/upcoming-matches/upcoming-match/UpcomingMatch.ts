export class UpcomingMatch {
  id: number;
  homeTeam: string;
  homeFlag: string;
  homeOdds: number;
  awayTeam: string;
  awayFlag: string;
  awayOdds: number;
  drawOdds: number;
  matchDate: Date;

  constructor(
    id: number,
    homeTeam: string,
    homeFlag: string,
    homeOdds: number,
    awayName: string,
    awayFlag: string,
    awayOdds: number,
    drawOdds: number,
    matchDate: Date
  ) {
    this.id = id;
    this.homeTeam = homeTeam;
    this.homeFlag = homeFlag;
    this.homeOdds = homeOdds;
    this.awayTeam = awayName;
    this.awayFlag = awayFlag;
    this.awayOdds = awayOdds;
    this.drawOdds = drawOdds;
    this.matchDate = matchDate;
  }
}
