-- Insert Competitions
INSERT INTO betapp.competition (name, code, type, season, emblem, start_date, end_date)
VALUES
    ('Serie A', 'SA', 'LEAGUE', 2023, 'https://crests.football-data.org/SA.png', '2023-08-19', '2024-05-26');

-- Insert Teams
INSERT INTO betapp.team (name, code, flag)
VALUES
    ('SSC Napoli', 'NAP', 'https://crests.football-data.org/113.svg'),
    ('FC Internazionale Milano', 'INT', 'https://crests.football-data.org/108.png'),
    ('Bologna FC 1909', 'BOL', 'https://crests.football-data.org/103.svg'),
    ('ACF Fiorentina', 'FIO', 'https://crests.football-data.org/99.svg');

-- Insert Scores
INSERT INTO betapp.score (home, away)
VALUES
    (2, 1),
    (1, 1),
    (0, 2);

-- Insert Results
INSERT INTO betapp.result (winner, duration, half_time, regular_time, extra_time, penalties, full_time, created_at, updated_at)
VALUES
    ('HOME_TEAM', 'REGULAR', 2, NULL, NULL, NULL, 1, NOW(), NULL),
    ('DRAW', 'REGULAR', 2, NULL, NULL, NULL, 2, NOW(), NULL),
    ('DRAW', 'REGULAR', 2, NULL, NULL, NULL, 2, NOW(), NULL),
    ('AWAY_TEAM', 'REGULAR', 3, NULL, NULL, NULL, 3, NOW(), NULL);

-- Insert Matches
INSERT INTO betapp.match (status, stage, "group", home_odds, away_odds, draw_odds, date, external_id, created_at, updated_at, competition, home_team, away_team, result)
VALUES
    ('FINISHED', 'REGULAR_SEASON', NULL, 2.0, 3.0, 2.5, '2023-02-01', 1, NOW(), NULL, 1, 1, 2, NULL),
    ('FINISHED', 'REGULAR_SEASON', NULL, 1.8, 2.5, 2.2, '2023-02-05', 2, NOW(), NULL, 1, 3, 4, NULL),
    ('SCHEDULED', 'REGULAR_SEASON', NULL, 1, 1, 1, '2023-02-10', 3, NOW(), NULL, 1, 4, 1, 1),
    ('TIMED', 'REGULAR_SEASON', NULL, 1, 1, 1, '2023-02-05', 4, NOW(), NULL, 1, 2, 3, 1);