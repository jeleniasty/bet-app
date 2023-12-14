package com.jeleniasty.betapp.features.team;

import com.jeleniasty.betapp.utils.GoogleSheetsService;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TeamNamesDictionary {

  private final GoogleSheetsService googleSheetsService;

  @Getter
  private Map<String, List<String>> teamNamesDictionary;

  public TeamNamesDictionary(GoogleSheetsService googleSheetsService) {
    this.googleSheetsService = googleSheetsService;
    this.teamNamesDictionary = getTeamNameVariations();
  }

  public void updateDictionary() {
    this.teamNamesDictionary = getTeamNameVariations();
  }

  private Map<String, List<String>> getTeamNameVariations() {
    Map<String, List<String>> dictionary = new HashMap<>();
    List<List<Object>> sheetRows;
    try {
      sheetRows = this.googleSheetsService.getSpreadsheetValues();
    } catch (GeneralSecurityException | IOException e) {
      throw new RuntimeException(e);
    }
    //TODO add more specific exception handling
    for (List<Object> row : sheetRows) {
      if (!row.isEmpty()) {
        var key = row.get(0).toString();
        List<String> alternativeTeamNames = row
          .subList(1, row.size())
          .stream()
          .map(Object::toString)
          .toList();

        dictionary.put(key, alternativeTeamNames);
      }
    }

    log.info("Team names dictionary synchronised with Spreadsheet");

    return dictionary;
  }
}
