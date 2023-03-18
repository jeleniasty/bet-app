package com.jeleniasty.betapp.match.service;

import com.jeleniasty.betapp.match.repository.MatchRepository;
import com.jeleniasty.betapp.match.repository.entity.Match;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    public void saveMatch(Integer homeTeamScore,
                          Integer awayTeamScore,
                          LocalDateTime endTime,
                          String homeTeamCode,
                          String awayTeamCode) {
        matchRepository.save(new Match(homeTeamScore, awayTeamScore, endTime, homeTeamCode, awayTeamCode));
    }

    public void deleteMatch(Long id) {
        matchRepository.deleteById(id);
    }

    public Optional<Match> getMatch(Long id) {
        return matchRepository.findById(id);
    }

    public Iterable<Match> getMatches() {
        return matchRepository.findAll();
    }
}
