import { Duration } from '../match/model/Duration';
import { BetType } from '../match/model/BetType';
import { Winner } from '../match/model/Winner';
import { ResultDTO, ScoreDTO } from '../match/model/ResultDTO';

export class Bet {
  public id: number;
  public type: BetType;
  public result: ResultDTO;
  public creationDate: Date;

  constructor(
    id: number,
    type: BetType,
    result: ResultDTO,
    creationDate: Date
  ) {
    this.id = id;
    this.type = type;
    this.result = result;
    this.creationDate = creationDate;
  }
}
