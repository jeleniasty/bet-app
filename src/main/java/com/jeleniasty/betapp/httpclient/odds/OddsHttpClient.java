package com.jeleniasty.betapp.httpclient.odds;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class OddsHttpClient {

  private final WebClient webClient;

  @Value("${betapp.theoddsapi.url}")
  private String baseUrl;

  @Value("${betapp.theoddsapi.apikey}")
  private String apiKey;

  public List<OddsResponse> getMatchData(String competition) {
    return webClient
      .get()
      .uri(constructGetMatchURL(competition))
      .retrieve()
      .bodyToFlux(OddsResponse.class)
      .collectList()
      .block();
  }

  private String constructGetMatchURL(String competitionKey) {
    return (
      baseUrl +
      "/v4/sports/" +
      competitionKey +
      "/odds/?apiKey=" +
      apiKey +
      "&regions=eu"
    );
  }
}
