import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardHeaderComponent } from './dashboard-header/dashboard-header.component';
import { UpcomingMatchComponent } from './dashboard/upcoming-matches/upcoming-match/upcoming-match.component';
import { UpcomingMatchesComponent } from './dashboard/upcoming-matches/upcoming-matches.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { BestPlayersComponent } from './dashboard/best-players/best-players.component';
import { UserScoreComponent } from './dashboard/best-players/user-score/user-score.component';
import { HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './authentication/login/login.component';
import { ReactiveFormsModule } from '@angular/forms';
import { httpInterceptorProviders } from './http-request-interceptor.service';
import { RegisterComponent } from './authentication/register/register.component';
import { CookieService } from 'ngx-cookie-service';
import { UserMenuComponent } from './dashboard-header/user-menu/user-menu.component';
import { AuthGuardService } from './authentication/auth-guard.service';
import { DatePipe } from '@angular/common';

@NgModule({
  declarations: [
    AppComponent,
    DashboardHeaderComponent,
    UpcomingMatchComponent,
    UpcomingMatchesComponent,
    DashboardComponent,
    BestPlayersComponent,
    UserScoreComponent,
    LoginComponent,
    RegisterComponent,
    UserMenuComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
  ],
  providers: [
    httpInterceptorProviders,
    CookieService,
    AuthGuardService,
    DatePipe,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
