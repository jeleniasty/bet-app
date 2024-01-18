import { Winner } from './Winner';
import { Duration } from './Duration';

export class ScoreDTO {
  home: number;
  away: number;

  constructor(home: number, away: number) {
    this.home = home;
    this.away = away;
  }
}

export class ResultDTO {
  public winner: Winner | null = null;
  public duration: Duration | null = null;
  public halfTimeScore: ScoreDTO | null = null;
  public regularTimeScore: ScoreDTO | null = null;
  public extraTimeScore: ScoreDTO | null = null;
  public penaltiesScore: ScoreDTO | null = null;
  public fullTimeScore: ScoreDTO | null = null;

  constructor(
    winner: Winner,
    duration?: Duration,
    halfTimeScore?: ScoreDTO,
    regularTimeScore?: ScoreDTO,
    extraTimeScore?: ScoreDTO,
    penaltiesScore?: ScoreDTO,
    fullTimeScore?: ScoreDTO
  ) {
    this.winner = winner || null;
    this.duration = duration || null;
    this.halfTimeScore = halfTimeScore || null;
    this.regularTimeScore = regularTimeScore || null;
    this.extraTimeScore = extraTimeScore || null;
    this.penaltiesScore = penaltiesScore || null;
    this.fullTimeScore = fullTimeScore || null;
  }
}
