import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Match } from './model/Match';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Winner } from './model/Winner';
import { CreateBetDTO } from './model/CreateBetDTO';
import { ResultDTO, ScoreDTO } from './model/ResultDTO';
import { BetType } from './model/BetType';
import { BetService } from '../bet/bet.service';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'betapp-match',
  templateUrl: './match.component.html',
  styleUrls: ['./match.component.css'],
})
export class MatchComponent implements OnInit {
  protected readonly Winner = Winner;
  matchId: number | null = null;
  match: Match | undefined;
  correctScoreBetForm: FormGroup;
  fullTimeResultWinner: Winner | undefined;

  isFullTimeResultFormExpanded: boolean = false;
  isCorrectScoreFormExpanded: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private http: HttpClient,
    private betService: BetService
  ) {
    this.correctScoreBetForm = this.formBuilder.group({
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
      new ResultDTO(this.fullTimeResultWinner),
      BetType.FULL_TIME_RESULT,
      +this.matchId
    );
    this.createBet(createBetDTO).subscribe(() => {
      this.betService.notifyBetCreated();
      this.resetFullTimeResultBetForm();
    });
  }

  resetFullTimeResultBetForm() {
    this.fullTimeResultWinner = undefined;
    this.isFullTimeResultFormExpanded = false;
  }

  submitCorrectScoreBet(): void {
    if (!this.matchId || this.correctScoreBetForm.invalid) {
      return;
    }

    const formValues = this.correctScoreBetForm.value;
    const createBetDTO: CreateBetDTO = new CreateBetDTO(
      new ResultDTO(
        this.determineCorrectScoreWinner(),
        undefined,
        undefined,
        undefined,
        undefined,
        undefined,
        new ScoreDTO(formValues.homeScore, formValues.awayScore)
      ),
      BetType.CORRECT_SCORE,
      +this.matchId
    );

    this.createBet(createBetDTO).subscribe(() => {
      this.betService.notifyBetCreated();
      this.resetCorrectScoreBetForm();
    });
  }

  resetCorrectScoreBetForm() {
    this.correctScoreBetForm.reset();
    this.isCorrectScoreFormExpanded = false;
  }

  private determineCorrectScoreWinner(): Winner {
    const home: number = this.correctScoreBetForm.get('homeScore')?.value;
    const away: number = this.correctScoreBetForm.get('awayScore')?.value;

    if (home > away) {
      return Winner.HOME_TEAM;
    }

    if (away > home) {
      return Winner.AWAY_TEAM;
    }

    return Winner.DRAW;
  }

  selectFullTimeResultWinner(winner: Winner): void {
    this.fullTimeResultWinner = winner;
  }

  createBet(createBetDTO: CreateBetDTO) {
    return this.http.post(environment.backendUrl + '/bet', createBetDTO);
  }

  getMatch(matchId: number): Observable<Match> {
    return this.http.get<Match>(environment.backendUrl + `/match/${matchId}`);
  }
}
