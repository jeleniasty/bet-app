package com.jeleniasty.betapp.features.odds;

import com.jeleniasty.betapp.httpclient.odds.OddsHttpClient;
import com.jeleniasty.betapp.httpclient.odds.OddsResponse;
import com.jeleniasty.betapp.httpclient.odds.Outcome;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.DoubleStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OddsService {

  private final OddsHttpClient oddsHttpClient;

  public List<MatchOddsDTO> getCompetitionOdds(String competitionKey) {
    var competitionOdds = this.oddsHttpClient.getOddsData(competitionKey);

    return competitionOdds.stream().map(this::getMatchOdds).toList();
  }

  private MatchOddsDTO getMatchOdds(OddsResponse oddsResponse) {
    var homeOdds = getAverageOdds(
      oddsResponse.outcomes().stream().mapToDouble(Outcome::homeOdds)
    );
    var awayOdds = getAverageOdds(
      oddsResponse.outcomes().stream().mapToDouble(Outcome::awayOdds)
    );
    var drawOdds = getAverageOdds(
      oddsResponse.outcomes().stream().mapToDouble(Outcome::drawOdds)
    );

    return new MatchOddsDTO(
      oddsResponse.homeTeam(),
      oddsResponse.awayTeam(),
      oddsResponse.commenceTime(),
      homeOdds,
      awayOdds,
      drawOdds
    );
  }

  private float getAverageOdds(DoubleStream odds) {
    return truncateToTwoDigitsPrecision(odds.average().orElse(1.00));
  }

  private float truncateToTwoDigitsPrecision(double value) {
    return BigDecimal
      .valueOf(value)
      .setScale(2, RoundingMode.HALF_UP)
      .floatValue();
  }
}
