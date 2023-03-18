package com.jeleniasty.betapp.matchbet.service;

import com.jeleniasty.betapp.matchbet.repository.MatchBetRepository;
import com.jeleniasty.betapp.matchbet.repository.entity.MatchBet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchBetService {

private final MatchBetRepository matchBetRepository;

    public void saveMatchBet(Integer homeTeamScore,
                             Integer awayTeamScore,
                             LocalDateTime betTime,
                             Long userId,
                             Long matchId) {
        matchBetRepository.save(new MatchBet(
                        homeTeamScore,
                        awayTeamScore,
                        betTime,
                        userId,
                        matchId));
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
