import { Team } from './Team';
import { Competition } from './Competition';

enum CompetitionStage {
  GROUP,
  LAST_OF_16,
  QUARTER_FINALS,
  SEMI_FINALS,
  THIRD_PLACE,
  FINAL,
}

enum MatchStatus {
  TIMED,
  IN_PROGRESS,
  FINISHED,
}

export class Match {
  id: number;
  homeTeam: Team;
  homeOdds: number;
  awayTeam: Team;
  awayOdds: number;
  stage: CompetitionStage;
  group: string;
  status: MatchStatus;
  competition: Competition;
  matchDate: Date;

  constructor(
    id: number,
    homeTeam: Team,
    homeOdds: number,
    awayTeam: Team,
    awayOdds: number,
    stage: CompetitionStage,
    group: string,
    status: MatchStatus,
    competition: Competition,
    matchDate: Date
  ) {
    this.id = id;
    this.homeTeam = homeTeam;
    this.homeOdds = homeOdds;
    this.awayTeam = awayTeam;
    this.awayOdds = awayOdds;
    this.stage = stage;
    this.group = group;
    this.status = status;
    this.competition = competition;
    this.matchDate = matchDate;
  }
}
