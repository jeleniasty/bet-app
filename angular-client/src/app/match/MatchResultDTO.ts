import { Winner } from './Winner';
import { Duration } from './Duration';
class ScoreDTO {
  private home: number;
  private away: number;

  constructor(home: number, away: number) {
    this.home = home;
    this.away = away;
  }
}

export class MatchResultDTO {
  private winner: Winner;
  private duration: Duration | null = null;
  private halfTimeScore: ScoreDTO | null = null;
  private regularTimeScore: ScoreDTO | null = null;
  private extraTimeScore: ScoreDTO | null = null;
  private penaltiesScore: ScoreDTO | null = null;

  constructor(
    winner: Winner,
    duration?: Duration,
    halfTimeScore?: ScoreDTO,
    regularTimeScore?: ScoreDTO,
    extraTimeScore?: ScoreDTO,
    penaltiesScore?: ScoreDTO
  ) {
    this.winner = winner;
    this.duration = duration || null;
    this.halfTimeScore = halfTimeScore || null;
    this.regularTimeScore = regularTimeScore || null;
    this.extraTimeScore = extraTimeScore || null;
    this.penaltiesScore = penaltiesScore || null;
  }
}
