import { BetType } from './BetType';
import { MatchResultDTO } from './MatchResultDTO';

export class CreateBetDTO {
  private matchResultDTO: MatchResultDTO;
  private betType: BetType;
  private matchId: number;

  constructor(
    matchResultDTO: MatchResultDTO,
    betType: BetType,
    matchId: number
  ) {
    this.matchResultDTO = matchResultDTO;
    this.betType = betType;
    this.matchId = matchId;
  }
}
