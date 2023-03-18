package com.jeleniasty.betapp.matchbet.controller;

import com.jeleniasty.betapp.matchbet.repository.MatchBetRepository;
import com.jeleniasty.betapp.matchbet.repository.entity.MatchBet;
import com.jeleniasty.betapp.matchbet.service.MatchBetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MatchBetController {

    private final MatchBetService matchBetService;

    @PostMapping("matchbet")
    public void saveMatchBet(@RequestBody MatchBet matchBet) {
        matchBetService.saveMatchBet(
                matchBet.getHomeTeamScore(),
                matchBet.getAwayTeamScore(),
                LocalDateTime.now(),
                matchBet.getUserId(),
                matchBet.getMatchId());
    }

    @DeleteMapping("matchbet/{id}")
    public void deleteMatch(@PathVariable Long id) {
        matchBetService.deleteMatchBet(id);
    }

    @GetMapping("matchbet/{id}")
    public Optional<MatchBet> getMatch(@PathVariable Long id) {
        return matchBetService.getMatchBet(id);
    }

    @GetMapping("matchbets")
    public Iterable<MatchBet> getMatches() {
        return matchBetService.getMatches();
    }
}
