import { BetType } from './BetType';
import { ResultDTO } from './ResultDTO';

export class CreateBetDTO {
  private resultDTO: ResultDTO;
  private betType: BetType;
  private matchId: number;

  constructor(resultDTO: ResultDTO, betType: BetType, matchId: number) {
    this.resultDTO = resultDTO;
    this.betType = betType;
    this.matchId = matchId;
  }
}
