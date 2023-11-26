package com.jeleniasty.betapp.httpclient.footballdata.match;

import com.jeleniasty.betapp.httpclient.footballdata.MatchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class MatchHttpClient {

  private final WebClient webClient;

  @Value("${betapp.url.footballdata}")
  private String baseUrl;

  @Value("${betapp.apikey.footballdata}")
  private String apiKey;

  public MatchResponse getMatchData(long matchExternalId) {
    return webClient
      .get()
      .uri(constructGetMatchURL(matchExternalId))
      .header("X-Auth-Token", apiKey)
      .retrieve()
      .bodyToMono(MatchResponse.class)
      .block();
  }

  private String constructGetMatchURL(long matchExternalId) {
    return (baseUrl + "/v4/matches/" + matchExternalId);
  }
}
