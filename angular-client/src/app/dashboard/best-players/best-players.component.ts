import { Component, OnInit } from '@angular/core';
import { UserScore } from './UserScore';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'betapp-best-players',
  templateUrl: './best-players.component.html',
  styleUrls: ['./best-players.component.css'],
})
export class BestPlayersComponent implements OnInit {
  users: UserScore[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.getUserScores().subscribe(
      (userScores: UserScore[]) =>
        (this.users = userScores.sort(
          (a: UserScore, b: UserScore) => b.points - a.points
        ))
    );
  }

  getUserScores(): Observable<UserScore[]> {
    return this.http.get<UserScore[]>(environment.backendUrl + '/user-scores');
  }
}
