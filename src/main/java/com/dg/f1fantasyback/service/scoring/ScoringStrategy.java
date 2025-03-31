package com.dg.f1fantasyback.service.scoring;

import com.dg.f1fantasyback.model.entity.racing.EventResult;

public interface ScoringStrategy {
    int calculateDriverPoints(EventResult result);

    int calculateTeamPoints(EventResult resultDriver1, EventResult resultDriver2);
}
