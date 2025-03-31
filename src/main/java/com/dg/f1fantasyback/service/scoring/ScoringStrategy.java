package com.dg.f1fantasyback.service.scoring;

import com.dg.f1fantasyback.model.entity.RaceResult;

public interface ScoringStrategy {
    int calculateDriverPoints(RaceResult result);

    int calculateTeamPoints(RaceResult resultDriver1, RaceResult resultDriver2);
}
