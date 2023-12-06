package com.jeleniasty.betapp.config;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleAuthorizationConfig {

  private static final JsonFactory JSON_FACTORY =
    GsonFactory.getDefaultInstance();

  @Value("${googlesheets.name}")
  private String applicationName;

  @Value("${googlesheets.credentials-filepath}")
  private String credentialsFilePath;

  @Value("${googlesheets.tokens-directorypath}")
  private String tokensDirectoryPath;

  public GoogleAuthorizationConfig() {}

  private Credential authorize() throws IOException, GeneralSecurityException {
    InputStream in =
      GoogleAuthorizationConfig.class.getResourceAsStream(credentialsFilePath);

    GoogleClientSecrets clientSecrets = null;
    if (in != null) {
      clientSecrets =
        GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
    }

    List<String> scopes = Collections.singletonList(
      SheetsScopes.SPREADSHEETS_READONLY
    );

    GoogleAuthorizationCodeFlow flow = null;
    if (clientSecrets != null) {
      flow =
        new GoogleAuthorizationCodeFlow.Builder(
          GoogleNetHttpTransport.newTrustedTransport(),
          JSON_FACTORY,
          clientSecrets,
          scopes
        )
          .setDataStoreFactory(
            new FileDataStoreFactory(new File(tokensDirectoryPath))
          )
          .setAccessType("offline")
          .build();
    }

    LocalServerReceiver localServerReceiver = new LocalServerReceiver.Builder()
      .setPort(8888)
      .build();
    return new AuthorizationCodeInstalledApp(flow, localServerReceiver)
      .authorize("user");
  }

  public Sheets getSheetsService()
    throws GeneralSecurityException, IOException {
    Credential credential = authorize();
    return new Sheets.Builder(
      GoogleNetHttpTransport.newTrustedTransport(),
      JSON_FACTORY,
      credential
    )
      .setApplicationName(applicationName)
      .build();
  }
}
//TODO refactor code
