CREATE TABLE user_team_teams
(
    teams_id     INTEGER NOT NULL,
    user_team_id BIGINT  NOT NULL,
    CONSTRAINT pk_user_team_teams PRIMARY KEY (teams_id, user_team_id)
);

ALTER TABLE user_team_teams
    ADD CONSTRAINT fk_useteatea_on_team FOREIGN KEY (teams_id) REFERENCES team (id);

ALTER TABLE user_team_teams
    ADD CONSTRAINT fk_useteatea_on_user_team FOREIGN KEY (user_team_id) REFERENCES user_team (id);