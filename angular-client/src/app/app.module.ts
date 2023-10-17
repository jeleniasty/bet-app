import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardHeaderComponent } from './dashboard-header/dashboard-header.component';
import { UpcomingMatchComponent } from './dashboard/upcoming-matches/upcoming-match/upcoming-match.component';
import { UpcomingMatchesComponent } from './dashboard/upcoming-matches/upcoming-matches.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ScoreboardComponent } from './dashboard/scoreboard/scoreboard.component';
import { UserScoreComponent } from './dashboard/scoreboard/user-score/user-score.component';

@NgModule({
  declarations: [
    AppComponent,
    DashboardHeaderComponent,
    UpcomingMatchComponent,
    UpcomingMatchesComponent,
    DashboardComponent,
    ScoreboardComponent,
    UserScoreComponent,
  ],
  imports: [BrowserModule, AppRoutingModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
