package com.jeleniasty.betapp.httpclient.footballdata;

import com.fasterxml.jackson.databind.JsonNode;
import com.jeleniasty.betapp.features.match.dto.MatchDTO;
import com.jeleniasty.betapp.features.match.model.CompetitionStage;
import com.jeleniasty.betapp.features.match.model.Group;
import com.jeleniasty.betapp.features.match.model.MatchStatus;
import com.jeleniasty.betapp.features.result.Duration;
import com.jeleniasty.betapp.features.result.ResultDTO;
import com.jeleniasty.betapp.features.result.Winner;
import com.jeleniasty.betapp.features.result.score.ScoreDTO;
import com.jeleniasty.betapp.features.team.TeamDTO;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class DeserializerUtils {

  public static MatchDTO mapToMatchDTO(JsonNode match) {
    TeamDTO homeTeamDTO = mapToTeamDTO(match.get("homeTeam"));
    TeamDTO awayTeamDTO = mapToTeamDTO(match.get("awayTeam"));
    MatchStatus matchStatus = MatchStatus.valueOf(match.get("status").asText());
    CompetitionStage competitionStage = CompetitionStage.valueOf(
      match.get("stage").asText()
    );
    Group group = null;
    if (!match.get("group").isNull()) {
      group = Group.valueOf(match.get("group").asText());
    }

    LocalDateTime matchDate = parseToLocalDateTime(
      match.get("utcDate").asText()
    );
    ResultDTO resultDTO = mapToResultDTO(match.get("score"));
    Long externalId = match.get("id").asLong();

    return new MatchDTO(
      null,
      homeTeamDTO,
      awayTeamDTO,
      1f,
      1f,
      1f,
      matchStatus,
      competitionStage,
      group,
      matchDate,
      resultDTO,
      externalId
    );
  }

  private static TeamDTO mapToTeamDTO(JsonNode team) {
    if (team.get("name").isNull()) {
      return null;
    }
    return new TeamDTO(
      null,
      team.get("name").asText(),
      team.get("tla").asText(),
      team.get("crest").asText()
    );
  }

  private static ResultDTO mapToResultDTO(JsonNode result) {
    JsonNode winnerNode = result.get("winner");
    if (winnerNode.isNull()) {
      return null;
    }

    Winner winner = Winner.valueOf(winnerNode.asText());
    Duration duration = Duration.valueOf(result.get("duration").asText());
    ScoreDTO halfTimeScore = null;
    ScoreDTO regularTimeScore = null;
    ScoreDTO extraTimeScore = null;
    ScoreDTO penaltiesScore = null;
    ScoreDTO fullTimeScore = null;

    if (result.has("halfTime")) {
      halfTimeScore =
        new ScoreDTO(
          result.get("halfTime").get("home").asInt(),
          result.get("halfTime").get("away").asInt()
        );
    }

    if (result.has("regularTime")) {
      regularTimeScore =
        new ScoreDTO(
          result.get("regularTime").get("home").asInt(),
          result.get("regularTime").get("away").asInt()
        );
    }

    if (result.has("extraTime")) {
      extraTimeScore =
        new ScoreDTO(
          result.get("extraTime").get("home").asInt(),
          result.get("extraTime").get("away").asInt()
        );
    }

    if (result.has("penalties")) {
      penaltiesScore =
        new ScoreDTO(
          result.get("penalties").get("home").asInt(),
          result.get("penalties").get("away").asInt()
        );
    }

    if (result.has("fullTime")) {
      fullTimeScore =
        new ScoreDTO(
          result.get("fullTime").get("home").asInt(),
          result.get("fullTime").get("away").asInt()
        );
    }
    return new ResultDTO(
      winner,
      duration,
      halfTimeScore,
      regularTimeScore,
      extraTimeScore,
      penaltiesScore,
      fullTimeScore
    );
  }

  private static LocalDateTime parseToLocalDateTime(String date) {
    return Instant.parse(date).atZone(ZoneOffset.UTC).toLocalDateTime();
  }
}
