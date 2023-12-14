package com.jeleniasty.betapp.features.team;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TeamController {

  private final TeamService teamService;

  @PostMapping("team/dictionary")
  public void synchroniseDictionary() {
    this.teamService.synchroniseDictionary();
  }
  //TODO add authorization

}
