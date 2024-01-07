package com.jeleniasty.betapp.httpclient.footballdata.competition;

import com.jeleniasty.betapp.features.competition.CompetitionDTO;
import com.jeleniasty.betapp.features.competition.CreateCompetitonRequest;
import com.jeleniasty.betapp.features.exceptions.CompetitionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class CompetitionHttpClient {

  private final WebClient webClient;

  @Value("${betapp.footballdata.url}")
  private String baseUrl;

  @Value("${betapp.footballdata.apikey}")
  private String apiKey;

  public CompetitionDTO getCompetitionMatchesData(
    CreateCompetitonRequest createCompetitonRequest
  ) {
    return webClient
      .get()
      .uri(constructGetCompetitionMatchesURL(createCompetitonRequest))
      .header("X-Auth-Token", apiKey)
      .retrieve()
      .onStatus(
        HttpStatus.NOT_FOUND::equals,
        response ->
          response
            .bodyToMono(String.class)
            .map(err ->
              new CompetitionNotFoundException(
                createCompetitonRequest.code(),
                createCompetitonRequest.season()
              )
            )
      )
      .bodyToMono(CompetitionDTO.class)
      .block();
  }

  private String constructGetCompetitionMatchesURL(
    CreateCompetitonRequest createCompetitonRequest
  ) {
    return (
      baseUrl +
      "/v4/competitions/" +
      createCompetitonRequest.code() +
      "/matches" +
      "?season=" +
      createCompetitonRequest.season()
    );
  }
}
