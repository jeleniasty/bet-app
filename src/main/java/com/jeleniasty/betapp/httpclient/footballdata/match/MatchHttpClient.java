package com.jeleniasty.betapp.httpclient.footballdata.match;

import com.jeleniasty.betapp.features.exceptions.RequestLimitExceededException;
import com.jeleniasty.betapp.features.match.dto.MatchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class MatchHttpClient {

  private final WebClient webClient;

  @Value("${betapp.footballdata.url}")
  private String baseUrl;

  @Value("${betapp.footballdata.apikey}")
  private String apiKey;

  public MatchDTO getMatchData(long matchExternalId) {
    return webClient
      .get()
      .uri(constructGetMatchURL(matchExternalId))
      .header("X-Auth-Token", apiKey)
      .retrieve()
      .onStatus(
        HttpStatus.TOO_MANY_REQUESTS::equals,
        response ->
          response
            .bodyToMono(String.class)
            .map(message ->
              new RequestLimitExceededException(
                constructGetMatchURL(matchExternalId)
              )
            )
      )
      .bodyToMono(MatchDTO.class)
      .block();
  }

  private String constructGetMatchURL(long matchExternalId) {
    return (baseUrl + "/v4/matches/" + matchExternalId);
  }
}
