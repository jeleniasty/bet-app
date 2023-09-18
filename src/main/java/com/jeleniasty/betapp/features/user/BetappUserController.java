package com.jeleniasty.betapp.features.user;

import com.jeleniasty.betapp.features.user.dto.UserScoreDTO;
import com.jeleniasty.betapp.features.user.service.BetappUserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class BetappUserController {

  private final BetappUserService betappUserService;

  @GetMapping("/score-board")
  public ResponseEntity<List<UserScoreDTO>> getScoreBoard() {
    return ResponseEntity.ok(betappUserService.fetchUserScores());
  }
}
