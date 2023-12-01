package com.jeleniasty.betapp.httpclient.odds;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class OddsResponseDeserializer extends StdDeserializer<OddsResponse> {

  protected OddsResponseDeserializer() {
    this(null);
  }

  protected OddsResponseDeserializer(Class vc) {
    super(vc);
  }

  @Override
  public OddsResponse deserialize(
    JsonParser jsonParser,
    DeserializationContext deserializationContext
  ) throws IOException {
    JsonNode oddsResponse = jsonParser.getCodec().readTree(jsonParser);
    String id = oddsResponse.get("id").asText();
    String competition = oddsResponse.get("sport_title").asText();
    String homeTeam = oddsResponse.get("home_team").asText();
    String awayTeam = oddsResponse.get("away_team").asText();
    LocalDateTime commenceTime = Instant
      .parse(oddsResponse.get("commence_time").asText())
      .atZone(ZoneOffset.UTC)
      .toLocalDateTime();
    List<Outcome> outcomes = new ArrayList<>();

    if (
      oddsResponse.has("bookmakers") && oddsResponse.get("bookmakers").isArray()
    ) {
      JsonNode bookmakers = oddsResponse.get("bookmakers");
      for (JsonNode bookmaker : bookmakers) {
        String title = bookmaker.get("title").asText();
        Instant lastUpdate = Instant.parse(
          bookmaker.get("last_update").asText()
        );

        if (bookmaker.has("markets") && bookmaker.get("markets").isArray()) {
          JsonNode market = bookmaker.get("markets").get(0);
          if (market.has("outcomes") && market.get("outcomes").isArray()) {
            JsonNode outcomesNode = market.get("outcomes");
            double homeOdds = 1;
            double awayOdds = 1;
            double drawOdds = 1;
            for (JsonNode outcomeNode : outcomesNode) {
              String outcomeName = outcomeNode.get("name").asText();
              double outcomePrice = outcomeNode.get("price").asDouble();

              if (outcomeName.equals(homeTeam)) {
                homeOdds = outcomePrice;
              } else if (outcomeName.equals(awayTeam)) {
                awayOdds = outcomePrice;
              } else if (outcomeName.equals("Draw")) {
                drawOdds = outcomePrice;
              }
            }
            outcomes.add(
              new Outcome(title, lastUpdate, homeOdds, awayOdds, drawOdds)
            );
          }
        }
      }
    }

    return new OddsResponse(
      id,
      competition,
      homeTeam,
      awayTeam,
      commenceTime,
      outcomes
    );
  }
}
