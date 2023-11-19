export class Team {
  id: number;
  name: string;
  code: string;
  flag: string;

  constructor(id: number, name: string, code: string, flag: string) {
    this.id = id;
    this.name = name;
    this.code = code;
    this.flag = flag;
  }
}
