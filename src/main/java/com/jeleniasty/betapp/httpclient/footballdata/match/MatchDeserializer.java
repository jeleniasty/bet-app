package com.jeleniasty.betapp.httpclient.footballdata.match;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.jeleniasty.betapp.features.match.dto.MatchDTO;
import com.jeleniasty.betapp.httpclient.footballdata.DeserializerUtils;
import java.io.IOException;

public class MatchDeserializer extends StdDeserializer<MatchDTO> {

  protected MatchDeserializer() {
    this(null);
  }

  protected MatchDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public MatchDTO deserialize(
    JsonParser jsonParser,
    DeserializationContext deserializationContext
  ) throws IOException {
    JsonNode match = jsonParser.getCodec().readTree(jsonParser);
    return DeserializerUtils.mapToMatchDTO(match);
  }
}
