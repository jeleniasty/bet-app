package com.jeleniasty.betapp.config;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "betapp.theoddsapi")
@Getter
@Setter
public class OddsApiProperties {

  private Map<String, String> competitionKey;
}
