ALTER TABLE race
    ADD is_calculated BOOLEAN DEFAULT FALSE;

ALTER TABLE race
    ALTER COLUMN is_calculated SET NOT NULL;