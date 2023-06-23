package com.jeleniasty.betapp.features.goal.matchbet.service;

import com.jeleniasty.betapp.features.goal.matchbet.repository.entity.MatchBet;
import com.jeleniasty.betapp.features.goal.matchbet.repository.MatchBetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchBetService {

private final MatchBetRepository matchBetRepository;

    public void saveMatchBet(MatchBet matchBet) {
        matchBetRepository.save(new MatchBet(
                        matchBet.getHomeTeamScore(),
                        matchBet.getAwayTeamScore(),
                        LocalDateTime.now(),
                        matchBet.getUserId(),
                        matchBet.getMatchId()));
    }

    public void deleteMatchBet(Long id) {
        matchBetRepository.deleteById(id);
    }

    public Optional<MatchBet> getMatchBet(Long id) {
        return matchBetRepository.findById(id);
    }

    public Iterable<MatchBet> getMatches() {
        return matchBetRepository.findAll();
    }
}
