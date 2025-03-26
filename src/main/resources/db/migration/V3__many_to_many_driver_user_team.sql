CREATE TABLE user_team_drivers
(
    driver_id    INTEGER NOT NULL,
    user_team_id BIGINT  NOT NULL,
    CONSTRAINT pk_user_team_drivers PRIMARY KEY (driver_id, user_team_id)
);

ALTER TABLE user_team_drivers
    ADD CONSTRAINT fk_useteadri_on_driver FOREIGN KEY (driver_id) REFERENCES driver (id);

ALTER TABLE user_team_drivers
    ADD CONSTRAINT fk_useteadri_on_user_team FOREIGN KEY (user_team_id) REFERENCES user_team (id);