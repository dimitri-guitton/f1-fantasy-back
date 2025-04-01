ALTER TABLE fantasy_team_constructors
DROP
CONSTRAINT fk_fanteacon_on_constructor;

ALTER TABLE fantasy_team_constructors
DROP
CONSTRAINT fk_fanteacon_on_fantasy_team;

ALTER TABLE fantasy_team_drivers
DROP
CONSTRAINT fk_fanteadri_on_driver;

ALTER TABLE fantasy_team_drivers
DROP
CONSTRAINT fk_fanteadri_on_fantasy_team;

DROP TABLE fantasy_team_constructors CASCADE;

DROP TABLE fantasy_team_drivers CASCADE;