CREATE SCHEMA IF NOT EXISTS betapp;

-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2023-01-25 15:06:11.18

-- tables
-- Table: Coach
CREATE TABLE betapp.Coach (
                       id int  NOT NULL,
                       first_Name varchar(40)  NOT NULL,
                       last_Name varchar(40)  NOT NULL,
                       team_code varchar(3)  NOT NULL,
                       CONSTRAINT Coach_pk PRIMARY KEY (id)
);

-- Table: Goal
CREATE TABLE betapp.Goal (
                      id int  NOT NULL,
                      timestamp smallint  NOT NULL,
                      match_id int  NOT NULL,
                      player_id int  NOT NULL,
                      CONSTRAINT Goal_pk PRIMARY KEY (id)
);

-- Table: Group
CREATE TABLE betapp."group" (
                         name char(1)  NOT NULL,
                         team1 smallint  NOT NULL,
                         team2 smallint  NOT NULL,
                         team3 smallint  NOT NULL,
                         team4 smallint  NOT NULL,
                         CONSTRAINT Group_pk PRIMARY KEY (name)
);

-- Table: Match
CREATE TABLE betapp.Match (
                       id int  NOT NULL,
                       team1_score int  NOT NULL,
                       team2_score int  NOT NULL,
                       end_time varchar(20)  NOT NULL,
                       team_code varchar(3)  NOT NULL,
                       team_2_code varchar(3)  NOT NULL,
                       CONSTRAINT Match_pk PRIMARY KEY (id)
);

-- Table: Match_Bet
CREATE TABLE betapp.Match_Bet (
                           id int  NOT NULL,
                           team1_score int  NOT NULL,
                           team2_score int  NOT NULL,
                           end_time varchar(20)  NOT NULL,
                           user_id int  NOT NULL,
                           match_id int  NOT NULL,
                           CONSTRAINT Match_Bet_pk PRIMARY KEY (id)
);

-- Table: Player
CREATE TABLE betapp.Player (
                        id int  NOT NULL,
                        first_Name varchar(40)  NOT NULL,
                        last_Name varchar(40)  NOT NULL,
                        age smallint  NOT NULL,
                        number smallint  NOT NULL,
                        is_Capitan boolean  NOT NULL,
                        team_code varchar(3)  NOT NULL,
                        yellow_cards smallint  NOT NULL,
                        red_cards smallint  NOT NULL,
                        CONSTRAINT Player_pk PRIMARY KEY (id)
);

-- Table: Team
CREATE TABLE betapp.Team (
                      team_code varchar(3)  NOT NULL,
                      name varchar(40)  NOT NULL,
                      group_name char(1)  NOT NULL,
                      matches_played smallint  NOT NULL,
                      wins smallint  NOT NULL,
                      ties smallint  NOT NULL,
                      loses smallint  NOT NULL,
                      points smallint  NOT NULL,
                      goals_scored smallint  NOT NULL,
                      goals_lost smallint  NOT NULL,
                      bilans_bramek smallint  NOT NULL,
                      CONSTRAINT Team_pk PRIMARY KEY (team_code)
);

-- Table: User
CREATE TABLE betapp."user" (
                        id int  NOT NULL,
                        login varchar(20)  NOT NULL,
                        password varchar(50)  NOT NULL,
                        email varchar(30)  NOT NULL,
                        points int  NOT NULL,
                        CONSTRAINT User_pk PRIMARY KEY (id)
);

-- foreign keys
-- Reference: Coach_Team (table: Coach)
ALTER TABLE betapp.Coach ADD CONSTRAINT Coach_Team
    FOREIGN KEY (team_code)
        REFERENCES betapp.Team (team_code)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: Goal_Match (table: Goal)
ALTER TABLE betapp.Goal ADD CONSTRAINT Goal_Match
    FOREIGN KEY (match_id)
        REFERENCES betapp.Match (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: Goal_Player (table: Goal)
ALTER TABLE betapp.Goal ADD CONSTRAINT Goal_Player
    FOREIGN KEY (player_id)
        REFERENCES betapp.Player (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: Match_Bet_Match (table: Match_Bet)
ALTER TABLE betapp.Match_Bet ADD CONSTRAINT Match_Bet_Match
    FOREIGN KEY (match_id)
        REFERENCES betapp.Match (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: Match_Bet_User (table: Match_Bet)
ALTER TABLE betapp.Match_Bet ADD CONSTRAINT Match_Bet_User
    FOREIGN KEY (user_id)
        REFERENCES betapp."user" (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: Match_Team1 (table: Match)
ALTER TABLE betapp.Match ADD CONSTRAINT Match_Team1
    FOREIGN KEY (team_code)
        REFERENCES betapp.Team (team_code)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: Match_Team2 (table: Match)
ALTER TABLE betapp.Match ADD CONSTRAINT Match_Team2
    FOREIGN KEY (team_2_code)
        REFERENCES betapp.Team (team_code)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: Player_Team (table: Player)
ALTER TABLE betapp.Player ADD CONSTRAINT Player_Team
    FOREIGN KEY (team_code)
        REFERENCES betapp.Team (team_code)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- Reference: Team_Group (table: Team)
ALTER TABLE betapp.Team ADD CONSTRAINT Team_Group
    FOREIGN KEY (group_name)
        REFERENCES betapp."group" (name)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

-- End of file.

