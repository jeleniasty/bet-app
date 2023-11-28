package com.jeleniasty.betapp.httpclient.odds;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class OddsHttpClient {

  private final WebClient webClient;

  @Value("${betapp.url.theoddsapi}")
  private String baseUrl;

  @Value("${betapp.apikey.theoddsapi}")
  private String apiKey;

  public OddsResponse[] getMatchData(String bookmaker) {
    return webClient
      .get()
      .uri(constructGetMatchURL())
      .retrieve()
      .bodyToMono(OddsResponse[].class)
      .block();
  }

  private String constructGetMatchURL() {
    return (
      baseUrl +
      "/v4/sports/soccer_uefa_champs_league/odds/?apiKey=" +
      apiKey +
      "&regions=eu"
    );
  }
}
