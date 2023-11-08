import { Duration } from '../match/Duration';
import { BetType } from '../match/BetType';

export class Bet {
  public id: number;
  public type: BetType;
  public homeScore: number;
  public awayScore: number;
  public creationDate: Date;
  public matchDuration: Duration | null;

  constructor(
    id: number,
    type: BetType,
    homeScore: number,
    awayScore: number,
    creationDate: Date,
    matchDuration?: Duration
  ) {
    this.id = id;
    this.type = type;
    this.homeScore = homeScore;
    this.awayScore = awayScore;
    this.creationDate = creationDate;
    this.matchDuration = matchDuration || null;
  }
}
