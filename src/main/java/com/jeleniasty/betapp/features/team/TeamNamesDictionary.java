package com.jeleniasty.betapp.features.team;

import com.jeleniasty.betapp.utils.GoogleSheetsService;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class TeamNamesDictionary {

  private final GoogleSheetsService googleSheetsService;

  @Getter
  private final Map<String, List<String>> teamNamesDictionary;

  public TeamNamesDictionary(GoogleSheetsService googleSheetsService) {
    this.googleSheetsService = googleSheetsService;
    try {
      this.teamNamesDictionary = createDictionary();
    } catch (GeneralSecurityException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  //TODO add more specific exception handling

  private Map<String, List<String>> createDictionary()
    throws GeneralSecurityException, IOException {
    Map<String, List<String>> dictionary = new HashMap<>();
    var sheetRows = this.googleSheetsService.getSpreadsheetValues();

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

    return dictionary;
  }
}
