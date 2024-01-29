package com.jeleniasty.betapp.config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import java.io.IOException;
import java.security.GeneralSecurityException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleAuthorizationConfig {

  private static final JsonFactory JSON_FACTORY =
    GsonFactory.getDefaultInstance();

  @Value("${googlesheets.name}")
  private String applicationName;

  @Getter
  @Value("${googlesheets.apiKey}")
  private String apiKey;

  public Sheets getSheetsService()
    throws GeneralSecurityException, IOException {
    // Create a simple HttpRequestInitializer (you can customize this based on your needs)
    HttpRequestInitializer requestInitializer = httpRequest -> {
      // No additional initialization for now
    };

    return new Sheets.Builder(
      GoogleNetHttpTransport.newTrustedTransport(),
      JSON_FACTORY,
      requestInitializer
    )
      .setApplicationName(applicationName)
      .build();
  }
}
//TODO refactor code
