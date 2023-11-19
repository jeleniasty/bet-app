package com.jeleniasty.betapp.httpclient.match;

import com.jeleniasty.betapp.features.competition.CreateCompetitonRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class MatchesHttpClient {

  @Value("${betapp.url.footballdata}")
  private String baseUrl;

  @Value("${betapp.apikey.footballdata}")
  private String apiKey;

  WebClient client = WebClient.create();

  public CompetitionMatchesResponse getCompetitionMatches(
    CreateCompetitonRequest createCompetitonRequest
  ) {
    return client
      .get()
      .uri(constructGetCompetitionMatchesURL(createCompetitonRequest))
      .header("X-Auth-Token", apiKey)
      .retrieve()
      .bodyToMono(CompetitionMatchesResponse.class)
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
