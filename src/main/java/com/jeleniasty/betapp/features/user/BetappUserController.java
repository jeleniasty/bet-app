package com.jeleniasty.betapp.features.user;

import com.jeleniasty.betapp.features.user.dto.UserScoreDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BetappUserController {

  private final BetappUserService betappUserService;

  @GetMapping("/user-scores")
  public ResponseEntity<List<UserScoreDTO>> getUserScores() {
    return ResponseEntity.ok(betappUserService.getPlayerScores());
  }
}
