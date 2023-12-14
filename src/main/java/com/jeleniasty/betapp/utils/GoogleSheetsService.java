package com.jeleniasty.betapp.utils;

import com.jeleniasty.betapp.config.GoogleAuthorizationConfig;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleSheetsService {

  @Value("${googlesheets.spreadsheet-id}")
  private String spreadsheetId;

  private final GoogleAuthorizationConfig googleAuthorizationConfig;

  public List<List<Object>> getSpreadsheetValues()
    throws IOException, GeneralSecurityException {
    return googleAuthorizationConfig
      .getSheetsService()
      .spreadsheets()
      .values()
      .get(spreadsheetId, "Arkusz1")
      .execute()
      .getValues();
  }
}
//TODO GoogleSheetsService more reusable
