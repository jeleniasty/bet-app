enum CompetitionType {
  CUP,
  LEAGUE,
}

export class Competition {
  id: number;
  name: string;
  code: string;
  type: CompetitionType;
  season: number;

  constructor(
    id: number,
    name: string,
    code: string,
    type: CompetitionType,
    season: number
  ) {
    this.id = id;
    this.name = name;
    this.code = code;
    this.type = type;
    this.season = season;
  }
}
