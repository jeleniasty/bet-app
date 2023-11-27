package com.jeleniasty.betapp.httpclient.odds;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class BookmakerDeserializer extends StdDeserializer<Bookmaker> {

  protected BookmakerDeserializer(Class vc) {
    super(vc);
  }

  @Override
  public Bookmaker deserialize(
    JsonParser jsonParser,
    DeserializationContext deserializationContext
  ) throws IOException {
    JsonNode bookmaker = jsonParser.getCodec().readTree(jsonParser);

    String title = bookmaker.get("title").asText();
    Instant lastUpdate = Instant.parse(bookmaker.get("last_update").asText());

    List<Outcome> outcomes = new ArrayList<>();
    if (bookmaker.has("markets") && bookmaker.get("markets").isArray()) {
      JsonNode marketsNode = bookmaker.get("markets").get(0);
      if (
        marketsNode.has("outcomes") && marketsNode.get("outcomes").isArray()
      ) {
        JsonNode outcomesNode = marketsNode.get("outcomes");
        for (JsonNode outcomeNode : outcomesNode) {
          String outcomeName = outcomeNode.get("name").asText();
          double outcomePrice = outcomeNode.get("price").asDouble();
          outcomes.add(new Outcome(outcomeName, outcomePrice));
        }
      }
    }

    return new Bookmaker(title, lastUpdate, outcomes);
  }
}
