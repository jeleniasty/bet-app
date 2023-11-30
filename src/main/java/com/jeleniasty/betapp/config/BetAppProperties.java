package com.jeleniasty.betapp.config;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "betapp")
@Getter
@Setter
public class BetAppProperties {

  @NestedConfigurationProperty
  private TheOddsApiProperties theOddsApi;

  @Getter
  @Setter
  public static class TheOddsApiProperties {

    private Map<String, String> competitionKey;
  }
}
