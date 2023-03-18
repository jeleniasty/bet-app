package com.jeleniasty.betapp.match.controller;

import com.jeleniasty.betapp.match.repository.entity.Match;
import com.jeleniasty.betapp.match.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @PostMapping("match")
    public void saveMatch(@RequestBody Match match) {
        matchService.saveMatch(match.getHomeTeamScore(), match.getAwayTeamScore(), LocalDateTime.now(), match.getHomeTeamCode(), match.getAwayTeamCode());
    }

    @DeleteMapping("match/{id}")
    public void deleteMatch(@PathVariable Long id) {
        matchService.deleteMatch(id);
    }

    @GetMapping("match/{id}")
    public Optional<Match> getMatch(@PathVariable Long id) {
        return matchService.getMatch(id);
    }

    @GetMapping("matches")
    public Iterable<Match> getMatches() {
        return matchService.getMatches();
    }
}
