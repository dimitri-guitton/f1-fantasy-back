package com.dg.f1fantasyback.service.race_point_calculator;

import com.dg.f1fantasyback.model.entity.RaceResult;

public interface ScoringStrategy {
    int calculatePoints(RaceResult result);
}
