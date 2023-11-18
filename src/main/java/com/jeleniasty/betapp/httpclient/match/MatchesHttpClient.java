package com.jeleniasty.betapp.httpclient.match;

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
    String competitionCode
  ) {
    return client
      .get()
      .uri(constructGetCompetitionMatches(competitionCode))
      .header("X-Auth-Token", apiKey)
      .retrieve()
      .bodyToMono(CompetitionMatchesResponse.class)
      .block();
  }

  private String constructGetCompetitionMatches(String competitionCode) {
    return baseUrl + "/v4/competitions/" + competitionCode + "/matches";
  }
}
