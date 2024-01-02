CREATE SCHEMA IF NOT EXISTS betapp;

CREATE TABLE bet (
                id serial NOT NULL,
                type text NOT NULL,
                match bigint  NOT NULL,
                betapp_user bigint  NOT NULL,
                result bigint  NOT NULL,
                created_at timestamp NOT NULL,
                updated_at timestamp NULL,
                version int NOT NULL,
                CONSTRAINT bet_pk PRIMARY KEY (id)
);

CREATE TABLE betapp_user (
                id serial  NOT NULL,
                username text UNIQUE NOT NULL,
                password text  NOT NULL,
                email text UNIQUE NOT NULL,
                points real  NOT NULL,
                updated_at timestamp NULL,
                created_at timestamp NOT NULL,
                CONSTRAINT betapp_user_pk PRIMARY KEY (id)
);

CREATE TABLE betapp.role (
                id serial  NOT NULL,
                name text  NOT NULL,
                CONSTRAINT role_pk PRIMARY KEY (id)
);

CREATE TABLE betapp.user_role (
                role bigint  NOT NULL,
                betapp_user bigint  NOT NULL,
                CONSTRAINT user_role_pk PRIMARY KEY (role, betapp_user)
);

CREATE TABLE betapp.competition (
                id serial  NOT NULL,
                name text  NOT NULL,
                code char(5)  NULL,
                type char(10)  NOT NULL,
                season int  NOT NULL,
                emblem text NOT NULL,
                start_date date NOT NULL,
                end_date date NOT NULL,
                CONSTRAINT competition_pk PRIMARY KEY (id)
);

CREATE TABLE match (
                id serial  NOT NULL,
                status text  NOT NULL,
                stage text  NOT NULL,
                "group" varchar(7)  NULL,
                home_odds float4 NOT NULL,
                away_odds float4 NOT NULL,
                draw_odds float4 NOT NULL,
                date timestamp  NOT NULL,
                external_id bigint NOT NULL UNIQUE,
                created_at timestamp NOT NULL,
                updated_at timestamp  NULL,
                competition bigint  NOT NULL,
                result bigint  NULL,
                home_team bigint NULL,
                away_team bigint NULL,
                CONSTRAINT match_pk PRIMARY KEY (id)
);

CREATE TABLE betapp.result (
                id serial  NOT NULL,
                winner text  NOT NULL,
                duration text  NULL,
                half_time bigint  NULL,
                regular_time bigint  NULL,
                extra_time bigint  NULL,
                penalties bigint  NULL,
                full_time bigint NULL,
                created_at timestamp NOT NULL,
                updated_at timestamp NULL,
                CONSTRAINT result_pk PRIMARY KEY (id)
);

CREATE TABLE betapp.score (
                id serial  NOT NULL,
                home int  NOT NULL,
                away int  NOT NULL,
                CONSTRAINT score_pk PRIMARY KEY (id)
);

CREATE TABLE team (
                id serial  NOT NULL,
                name text  NOT NULL UNIQUE,
                code varchar(3)  NOT NULL,
                flag text NOT NULL,
                CONSTRAINT team_pk PRIMARY KEY (id)
);

ALTER TABLE match ADD CONSTRAINT home_team_match
    FOREIGN KEY (home_team)
        REFERENCES team (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

ALTER TABLE match ADD CONSTRAINT away_team_match
    FOREIGN KEY (away_team)
        REFERENCES team (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

ALTER TABLE betapp.result ADD CONSTRAINT full_time_score
    FOREIGN KEY (full_time)
        REFERENCES betapp.score (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

ALTER TABLE betapp.result ADD CONSTRAINT extra_time_score
    FOREIGN KEY (penalties)
        REFERENCES betapp.score (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

ALTER TABLE betapp.result ADD CONSTRAINT half_time_score
    FOREIGN KEY (regular_time)
        REFERENCES betapp.score (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

ALTER TABLE bet ADD CONSTRAINT match_bet_match
    FOREIGN KEY (match)
        REFERENCES match (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

ALTER TABLE bet ADD CONSTRAINT match_bet_result
    FOREIGN KEY (result)
        REFERENCES betapp.result (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

ALTER TABLE bet ADD CONSTRAINT match_bet_user
    FOREIGN KEY (betapp_user)
        REFERENCES betapp_user (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

ALTER TABLE match ADD CONSTRAINT match_competition
    FOREIGN KEY (competition)
        REFERENCES betapp.competition (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

ALTER TABLE match ADD CONSTRAINT match_result
    FOREIGN KEY (result)
        REFERENCES betapp.result (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

ALTER TABLE betapp.result ADD CONSTRAINT penalties_score
    FOREIGN KEY (extra_time)
        REFERENCES betapp.score (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

ALTER TABLE betapp.result ADD CONSTRAINT regular_time_score
    FOREIGN KEY (half_time)
        REFERENCES betapp.score (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

ALTER TABLE betapp.user_role ADD CONSTRAINT user_role_betapp_user
    FOREIGN KEY (betapp_user)
        REFERENCES betapp.betapp_user (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;

ALTER TABLE betapp.user_role ADD CONSTRAINT user_role_role
    FOREIGN KEY (role)
        REFERENCES betapp.role (id)
        NOT DEFERRABLE
            INITIALLY IMMEDIATE
;