import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Match } from './Match';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Duration } from './Duration';
import { Winner } from './Winner';

@Component({
  selector: 'betapp-match',
  templateUrl: './match.component.html',
  styleUrls: ['./match.component.css'],
})
export class MatchComponent implements OnInit {
  match: Match | undefined;
  correctScoreBetForm: FormGroup;
  fullTimeResultWinner: Winner | undefined;

  isFullTimeResultFormExpanded: boolean = false;
  isCorrectScoreFormExpanded: boolean = false;
  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private http: HttpClient
  ) {
    this.correctScoreBetForm = this.formBuilder.group({
      duration: [Duration.REGULAR_TIME, Validators.required],
      home: ['', [Validators.required, Validators.min(0), Validators.max(200)]],
      away: ['', [Validators.required, Validators.min(0), Validators.max(200)]],
    });
  }

  ngOnInit(): void {
    const matchId = this.route.snapshot.paramMap.get('id');
    if (matchId) {
      this.getMatch(+matchId).subscribe((match: Match) => {
        console.log(match);
        this.match = match;
      });
    }
  }

  selectFullTimeResultWinner(winner: Winner): void {
    this.fullTimeResultWinner = winner;
  }

  toggleFullTimeResultForm(): void {
    this.isFullTimeResultFormExpanded = !this.isFullTimeResultFormExpanded;
  }

  toggleCorrectScoreForm(): void {
    this.isCorrectScoreFormExpanded = !this.isCorrectScoreFormExpanded;
  }

  getMatch(matchId: number): Observable<Match> {
    return this.http.get<Match>(`http://localhost:8080/match/${matchId}`);
  }

  protected readonly Duration = Duration;
  protected readonly Winner = Winner;
}
