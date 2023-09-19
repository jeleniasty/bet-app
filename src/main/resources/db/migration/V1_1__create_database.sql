CREATE SCHEMA IF NOT EXISTS betapp;

CREATE TABLE betapp.coach (
                       id SERIAL NOT NULL,
                       first_name varchar(40)  NULL,
                       last_name varchar(40)  NOT NULL,
                       team_code varchar(3)  NOT NULL,
                       CONSTRAINT coach_pk PRIMARY KEY (id)
);

CREATE TABLE betapp.goal (
                      id SERIAL NOT NULL,
                      timestamp smallint  NOT NULL,
                      match_id int  NOT NULL,
                      player_id int  NOT NULL,
                      CONSTRAINT goal_pk PRIMARY KEY (id)
);

CREATE TABLE betapp."group" (
                         group_name char(1)  NOT NULL,
                         team1 smallint  NULL,
                         team2 smallint  NULL,
                         team3 smallint  NULL,
                         team4 smallint  NULL,
                         CONSTRAINT group_pk PRIMARY KEY (group_name)
);

CREATE TABLE betapp.match (
                       id SERIAL NOT NULL,
                       home_team_code varchar(3) NOT NULL,
                       away_team_code varchar(3) NOT NULL,
                       start_time timestamp NOT NULL,
                       stadium_id varchar(255) NOT NULL,
                       match_result_id int NULL,
                       CONSTRAINT match_pk PRIMARY KEY (id)
);

CREATE TABLE betapp.match_result (
                        id BIGSERIAL NOT NULL,
                        home_team_score int NOT NULL,
                        away_team_score int NOT NULL ,
                        duration smallint,
                        CONSTRAINT match_result_pk PRIMARY KEY (id)
);

CREATE TABLE betapp.bet (
                           id SERIAL NOT NULL,
                           match_result_id inT NOT NULL,
                           user_id int  NOT NULL,
                           match_id int  NOT NULL,
                           created_at timestamp  NOT NULL,
                           updated_at timestamp,
                           version int NOT NULL,
                           CONSTRAINT bet_pk PRIMARY KEY (id)
);

CREATE TABLE betapp.player (
                        id SERIAL NOT NULL,
                        first_name varchar(40)  NULL,
                        last_name varchar(40)  NOT NULL,
                        player_number smallint  NOT NULL,
                        team_code varchar(3)  NOT NULL,
                        yellow_cards smallint  NULL,
                        red_cards smallint  NULL,
                        position varchar(12)  NOT NULL,
                        CONSTRAINT player_pk PRIMARY KEY (id)
);

CREATE TABLE betapp.team (
                      team_code varchar(3)  NOT NULL,
                      team_name varchar(40)  NOT NULL,
                      group_name char(1)  NOT NULL,
                      matches_played smallint  NOT NULL,
                      wins smallint  NOT NULL,
                      ties smallint  NOT NULL,
                      loses smallint  NOT NULL,
                      points smallint  NOT NULL,
                      goals_scored smallint  NOT NULL,
                      goals_lost smallint  NOT NULL,
                      CONSTRAINT team_pk PRIMARY KEY (team_code)
);

CREATE TABLE betapp.betapp_user (
                        id SERIAL NOT NULL,
                        username varchar(255)  NOT NULL,
                        email varchar(255)  NOT NULL,
                        password varchar(255)  NOT NULL,
                        role varchar(255) NOT NULL,
                        score int NOT NULL DEFAULT 0,
                        total_bets int NOT NULL DEFAULT 0,
                        created_at timestamp NOT NULL,
                        updated_at timestamp,
                        CONSTRAINT user_pk PRIMARY KEY (id)
);


ALTER TABLE betapp.coach ADD CONSTRAINT coach_team
    FOREIGN KEY (team_code)
        REFERENCES betapp.team (team_code);

ALTER TABLE betapp.goal ADD CONSTRAINT goal_match
    FOREIGN KEY (match_id)
        REFERENCES betapp.match (id);

ALTER TABLE betapp.goal ADD CONSTRAINT goal_player
    FOREIGN KEY (player_id)
        REFERENCES betapp.player (id);

ALTER TABLE betapp.bet ADD CONSTRAINT bet_match
    FOREIGN KEY (match_id)
        REFERENCES betapp.match (id);

ALTER TABLE betapp.bet ADD CONSTRAINT bet_user
    FOREIGN KEY (user_id)
        REFERENCES betapp.betapp_user (id);

ALTER TABLE betapp.match ADD CONSTRAINT match_team1
    FOREIGN KEY (home_team_code)
        REFERENCES betapp.team (team_code);

ALTER TABLE betapp.match ADD CONSTRAINT match_team2
    FOREIGN KEY (away_team_code)
        REFERENCES betapp.team (team_code);

ALTER TABLE betapp.match ADD CONSTRAINT match_match_result
    FOREIGN KEY (match_result_id)
        REFERENCES betapp.match_result (id);

ALTER TABLE betapp.player ADD CONSTRAINT player_team
    FOREIGN KEY (team_code)
        REFERENCES betapp.team (team_code);

ALTER TABLE betapp.team ADD CONSTRAINT team_group
    FOREIGN KEY (group_name)
        REFERENCES betapp."group" (group_name);
