ALTER TABLE race_result
    ADD nb_overtakes INTEGER DEFAULT 0;

ALTER TABLE race_result
    ALTER COLUMN nb_overtakes SET NOT NULL;