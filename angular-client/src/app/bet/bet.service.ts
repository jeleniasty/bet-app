import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Bet } from './Bet';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class BetService {
  private betCreatedSubject = new Subject<void>();
  constructor(private http: HttpClient) {}

  notifyBetCreated(): void {
    this.betCreatedSubject.next();
  }

  onBetCreated(): Observable<void> {
    return this.betCreatedSubject.asObservable();
  }

  getUserBets(matchId: number): Observable<Bet[]> {
    return this.http.get<Bet[]>(`http://localhost:8080/bets/user/${matchId}`);
  }
}
