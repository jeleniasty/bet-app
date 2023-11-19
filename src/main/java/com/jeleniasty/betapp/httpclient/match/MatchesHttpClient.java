package com.jeleniasty.betapp.httpclient.match;

import com.jeleniasty.betapp.features.competition.CreateCompetitonRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class MatchesHttpClient {

  private final WebClient webClient;

  @Value("${betapp.url.footballdata}")
  private String baseUrl;

  @Value("${betapp.apikey.footballdata}")
  private String apiKey;

  public CompetitionMatchesResponse getCompetitionMatches(
    CreateCompetitonRequest createCompetitonRequest
  ) {
    return webClient
      .get()
      .uri(constructGetCompetitionMatchesURL(createCompetitonRequest))
      .header("X-Auth-Token", apiKey)
      .retrieve()
      .bodyToMono(CompetitionMatchesResponse.class)
      .block();
    //TODO create async calls without blocking code
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
