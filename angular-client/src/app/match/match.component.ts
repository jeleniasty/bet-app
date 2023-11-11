import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Match } from './Match';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Duration } from './Duration';
import { Winner } from './Winner';
import { CreateBetDTO } from './CreateBetDTO';
import { MatchResultDTO, ScoreDTO } from './MatchResultDTO';
import { BetType } from './BetType';

@Component({
  selector: 'betapp-match',
  templateUrl: './match.component.html',
  styleUrls: ['./match.component.css'],
})
export class MatchComponent implements OnInit {
  matchId: number | null = null;
  match: Match | undefined;
  correctScoreBetForm: FormGroup;
  fullTimeResultWinner: Winner | undefined;
  correctScoreWinner: Winner | undefined;

  isFullTimeResultFormExpanded: boolean = false;
  isCorrectScoreFormExpanded: boolean = false;
  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private http: HttpClient
  ) {
    this.correctScoreBetForm = this.formBuilder.group({
      duration: [Duration.REGULAR_TIME, Validators.required],
      homeScore: [
        '',
        [Validators.required, Validators.min(0), Validators.max(200)],
      ],
      awayScore: [
        '',
        [Validators.required, Validators.min(0), Validators.max(200)],
      ],
    });
  }

  ngOnInit(): void {
    const idString = this.route.snapshot.paramMap.get('id');
    if (idString) {
      this.matchId = +idString;
    }

    if (this.matchId) {
      this.getMatch(+this.matchId).subscribe((match: Match) => {
        console.log(match);
        this.match = match;
      });
    }
  }

  toggleFullTimeResultForm(): void {
    this.isFullTimeResultFormExpanded = !this.isFullTimeResultFormExpanded;
    this.isCorrectScoreFormExpanded = false;
  }

  toggleCorrectScoreForm(): void {
    this.isCorrectScoreFormExpanded = !this.isCorrectScoreFormExpanded;
    this.isFullTimeResultFormExpanded = false;
  }

  submitFullTimeResultBet(): void {
    if (!this.fullTimeResultWinner || !this.matchId) {
      return;
    }

    const createBetDTO: CreateBetDTO = new CreateBetDTO(
      new MatchResultDTO(this.fullTimeResultWinner),
      BetType.FULL_TIME_RESULT,
      +this.matchId
    );
    this.createBet(createBetDTO).subscribe();
  }

  submitCorrectScoreBet(): void {
    if (!this.matchId || this.correctScoreBetForm.invalid) {
      return;
    }

    const formValues = this.correctScoreBetForm.value;
    const createBetDTO: CreateBetDTO = new CreateBetDTO(
      this.constructCorrectScoreMatchResult(formValues),
      BetType.CORRECT_SCORE,
      +this.matchId
    );

    this.createBet(createBetDTO).subscribe();
  }

  private constructCorrectScoreMatchResult(formValues: any): MatchResultDTO {
    if (formValues.duration === Duration.REGULAR_TIME) {
      return new MatchResultDTO(
        this.determineCorrectScoreWinner(),
        formValues.duration,
        undefined,
        new ScoreDTO(formValues.homeScore, formValues.awayScore)
      );
    }

    if (formValues.duration === Duration.EXTRA_TIME) {
      return new MatchResultDTO(
        this.determineCorrectScoreWinner(),
        formValues.duration,
        undefined,
        undefined,
        new ScoreDTO(formValues.homeScore, formValues.awayScore)
      );
    }

    return new MatchResultDTO(
      this.determineCorrectScoreWinner(),
      formValues.duration,
      undefined,
      undefined,
      undefined,
      new ScoreDTO(formValues.homeScore, formValues.awayScore)
    );
  }

  private determineCorrectScoreWinner(): Winner {
    const home: number = this.correctScoreBetForm.get('homeScore')?.value;
    const away: number = this.correctScoreBetForm.get('awayScore')?.value;

    if (home > away) {
      return Winner.HOME;
    }

    if (away > home) {
      return Winner.AWAY;
    }

    return Winner.DRAW;
  }

  selectFullTimeResultWinner(winner: Winner): void {
    this.fullTimeResultWinner = winner;
  }

  createBet(createBetDTO: CreateBetDTO) {
    console.log(createBetDTO);
    return this.http.post('http://localhost:8080/bet', createBetDTO);
  }
  getMatch(matchId: number): Observable<Match> {
    return this.http.get<Match>(`http://localhost:8080/match/${matchId}`);
  }

  protected readonly Duration = Duration;
  protected readonly Winner = Winner;
}
