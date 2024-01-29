package com.jeleniasty.betapp.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

  @Bean
  public WebClient webClient() {
    final int size = 16 * 1024 * 1024;
    final ExchangeStrategies exchangeStrategies = ExchangeStrategies
      .builder()
      .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
      .build();
    return WebClient.builder().exchangeStrategies(exchangeStrategies).build();
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(@NotNull CorsRegistry registry) {
        registry
          .addMapping("/**")
          .allowedOrigins("http://54.93.69.175:4200", "http://localhost:4200");
      }
    };
  }
}
