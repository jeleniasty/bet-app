import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Match } from './Match';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'betapp-match',
  templateUrl: './match.component.html',
  styleUrls: ['./match.component.css'],
})
export class MatchComponent implements OnInit {
  match: Match | undefined;
  betForm: FormGroup;
  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private http: HttpClient
  ) {
    this.betForm = this.formBuilder.group({
      home: ['', [Validators.required, Validators.min(0), Validators.max(200)]],
      away: ['', [Validators.required, Validators.min(0), Validators.max(200)]],
    });
  }

  ngOnInit() {
    const matchId = this.route.snapshot.paramMap.get('id');
    if (matchId) {
      this.getMatch(+matchId).subscribe((match: Match) => {
        console.log(match);
        this.match = match;
      });
    }
  }

  getMatch(matchId: number): Observable<Match> {
    return this.http.get<Match>(`http://localhost:8080/match/${matchId}`);
  }
}
