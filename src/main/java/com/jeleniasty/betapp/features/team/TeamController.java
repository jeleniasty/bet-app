package com.jeleniasty.betapp.features.team;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TeamController {

  private final TeamService teamService;

  @PostMapping("team/dictionary")
  @PreAuthorize(value = "hasAuthority('ADMIN')")
  public void synchroniseDictionary() {
    this.teamService.synchroniseDictionary();
  }
}
