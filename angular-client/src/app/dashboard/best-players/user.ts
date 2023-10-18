export class User {
  username: string;
  score: number;
  betsCount: number;
  credits: number;
  icon: string;

  constructor(
    username: string,
    score: number,
    betsCount: number,
    credits: number,
    icon: string
  ) {
    this.username = username;
    this.score = score;
    this.betsCount = betsCount;
    this.credits = credits;
    this.icon = icon;
  }
}
