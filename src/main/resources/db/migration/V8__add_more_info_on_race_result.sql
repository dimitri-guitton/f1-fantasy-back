ALTER TABLE race_result
    ADD driver_of_the_day BOOLEAN DEFAULT FALSE;

ALTER TABLE race_result
    ADD fastest_lap BOOLEAN DEFAULT FALSE;

ALTER TABLE race_result
    ALTER COLUMN driver_of_the_day SET NOT NULL;

ALTER TABLE race_result
    ALTER COLUMN fastest_lap SET NOT NULL;