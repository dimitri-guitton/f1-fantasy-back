ALTER TABLE race_result
    ADD end_position INTEGER;

ALTER TABLE race_result
    ADD start_position INTEGER;

UPDATE race_result
SET start_position = 0;

UPDATE race_result
SET end_position = position;

ALTER TABLE race_result
    ALTER COLUMN end_position SET NOT NULL;

ALTER TABLE race_result
DROP
COLUMN position;