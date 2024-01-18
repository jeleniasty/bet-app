import { Duration } from '../match/model/Duration';
import { BetType } from '../match/model/BetType';
import { Winner } from '../match/model/Winner';
import { ScoreDTO } from '../match/model/ResultDTO';

export class Bet {
  public id: number;
  public type: BetType;
  public winner: Winner;
  public matchDuration: Duration | null;
  public halfTimeScore: ScoreDTO | null;
  public regularTimeScore: ScoreDTO | null;
  public extraTimeScore: ScoreDTO | null;
  public penaltiesScore: ScoreDTO | null;
  public fullTimeScore: ScoreDTO | null;
  public creationDate: Date;

  constructor(
    id: number,
    type: BetType,
    winner: Winner,
    creationDate: Date,
    regularTimeScore: ScoreDTO | null,
    matchDuration: Duration | null,
    halfTimeScore: ScoreDTO | null,
    extraTimeScore: ScoreDTO | null,
    fullTimeScore: ScoreDTO | null,
    penaltiesScore: ScoreDTO | null
  ) {
    this.id = id;
    this.type = type;
    this.winner = winner;
    this.matchDuration = matchDuration;
    this.halfTimeScore = halfTimeScore;
    this.regularTimeScore = regularTimeScore;
    this.extraTimeScore = extraTimeScore;
    this.penaltiesScore = penaltiesScore;
    this.fullTimeScore = fullTimeScore;
    this.creationDate = creationDate;
  }
}
