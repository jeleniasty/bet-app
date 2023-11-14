export class UserScore {
  id: number;
  username: string;
  points: number;
  icon: string;

  constructor(id: number, username: string, points: number, icon: string) {
    this.id = id;
    this.username = username;
    this.points = points;
    this.icon = icon;
  }
}
