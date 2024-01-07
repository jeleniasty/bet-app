package com.jeleniasty.betapp.httpclient.footballdata;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.jeleniasty.betapp.features.competition.CompetitionDTO;
import com.jeleniasty.betapp.features.competition.CompetitionType;
import com.jeleniasty.betapp.features.match.dto.MatchDTO;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CompetitionDeserializer extends StdDeserializer<CompetitionDTO> {

  protected CompetitionDeserializer() {
    this(null);
  }

  protected CompetitionDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public CompetitionDTO deserialize(
    JsonParser jsonParser,
    DeserializationContext deserializationContext
  ) throws IOException {
    JsonNode competitionMatchesResponse = jsonParser
      .getCodec()
      .readTree(jsonParser);

    int season = competitionMatchesResponse
      .get("filters")
      .get("season")
      .asInt();

    JsonNode resultSet = competitionMatchesResponse.get("resultSet");
    LocalDate startDate = LocalDate.parse(resultSet.get("first").asText());
    LocalDate endDate = LocalDate.parse(resultSet.get("last").asText());

    JsonNode competition = competitionMatchesResponse.get("competition");
    String competitionName = competition.get("name").asText();
    String competitionCode = competition.get("code").asText();
    CompetitionType competitionType = CompetitionType.valueOf(
      competition.get("type").asText()
    );
    String competitionEmblem = competition.get("emblem").asText();

    List<MatchDTO> matchDTOs = new ArrayList<>();

    JsonNode matches = competitionMatchesResponse.get("matches");
    matches.forEach(match ->
      matchDTOs.add(DeserializerUtils.mapToMatchDTO(match))
    );
    return new CompetitionDTO(
      null,
      competitionName,
      competitionCode,
      competitionType,
      season,
      competitionEmblem,
      startDate,
      endDate,
      matchDTOs
    );
  }
}
