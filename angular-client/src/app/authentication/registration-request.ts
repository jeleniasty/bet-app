export class RegistrationRequest {
  username: string;
  email: string;
  password: string;
  roleNames: string[];

  constructor(
    username: string,
    email: string,
    password: string,
    roleNames: string[]
  ) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.roleNames = roleNames;
  }
}
