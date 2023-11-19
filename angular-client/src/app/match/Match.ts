import { Team } from './Team';
import { Competition } from './Competition';

enum CompetitionStage {
  FINAL = 'FINAL',
  THIRD_PLACE = 'THIRD_PLACE',
  SEMI_FINALS = 'SEMI_FINALS',
  QUARTER_FINALS = 'QUARTER_FINALS',
  LAST_16 = 'LAST_16',
  LAST_32 = 'LAST_32',
  LAST_64 = 'LAST_64',
  ROUND_1 = 'ROUND_1',
  ROUND_2 = 'ROUND_2',
  ROUND_3 = 'ROUND_3',
  ROUND_4 = 'ROUND_4',
  GROUP_STAGE = 'GROUP_STAGE',
  PRELIMINARY_ROUND = 'PRELIMINARY_ROUND',
  QUALIFICATION = 'QUALIFICATION',
  QUALIFICATION_ROUND_1 = 'QUALIFICATION_ROUND_1',
  QUALIFICATION_ROUND_2 = 'QUALIFICATION_ROUND_2',
  QUALIFICATION_ROUND_3 = 'QUALIFICATION_ROUND_3',
  PLAYOFF_ROUND_1 = 'PLAYOFF_ROUND_1',
  PLAYOFF_ROUND_2 = 'PLAYOFF_ROUND_2',
  PLAYOFFS = 'PLAYOFFS',
  REGULAR_SEASON = 'REGULAR_SEASON',
  CLAUSURA = 'CLAUSURA',
  APERTURA = 'APERTURA',
  CHAMPIONSHIP_ROUND = 'CHAMPIONSHIP_ROUND',
  RELEGATION_ROUND = 'RELEGATION_ROUND',
}

enum MatchStatus {
  SCHEDULED = 'SCHEDULED',
  TIMED = 'TIMED',
  CANCELLED = 'CANCELLED',
  POSTPONED = 'POSTPONED',
  SUSPENDED = 'SUSPENDED',
  AWARDED = 'AWARDED',
  IN_PLAY = 'IN_PLAY',
  PAUSED = 'PAUSED',
  FINISHED = 'FINISHED',
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
