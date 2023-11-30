package com.jeleniasty.betapp.features.odds;

import com.jeleniasty.betapp.httpclient.odds.OddsHttpClient;
import com.jeleniasty.betapp.httpclient.odds.OddsResponse;
import com.jeleniasty.betapp.httpclient.odds.Outcome;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OddsService {

  private final OddsHttpClient oddsHttpClient;

  public List<MatchOddsDTO> getCompetitionOdds(String competitionKey) {
    var competitionOdds = this.oddsHttpClient.getMatchData(competitionKey);

    return competitionOdds.stream().map(this::getMatchOdds).toList();
  }

  private MatchOddsDTO getMatchOdds(OddsResponse oddsResponse) {
    var homeOdds = oddsResponse
      .outcomes()
      .stream()
      .mapToDouble(Outcome::homeOdds)
      .average()
      .orElse(1.00);
    var awayOdds = oddsResponse
      .outcomes()
      .stream()
      .mapToDouble(Outcome::awayOdds)
      .average()
      .orElse(1.00);
    var drawOdds = oddsResponse
      .outcomes()
      .stream()
      .mapToDouble(Outcome::drawOdds)
      .average()
      .orElse(1.00);

    return new MatchOddsDTO(
      oddsResponse.homeTeam(),
      oddsResponse.awayTeam(),
      oddsResponse.commenceTime(),
      (float) homeOdds,
      (float) awayOdds,
      (float) drawOdds
    );
  }
}
