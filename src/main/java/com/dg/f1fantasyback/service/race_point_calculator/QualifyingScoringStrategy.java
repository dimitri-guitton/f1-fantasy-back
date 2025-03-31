package com.dg.f1fantasyback.service.race_point_calculator;

import com.dg.f1fantasyback.model.entity.RaceResult;
import com.dg.f1fantasyback.model.enums.RaceTypeEnum;

public class QualifyingScoringStrategy implements ScoringStrategy {
    private static final int DNF_PENALTY = -5;

    @Override
    public int calculatePoints(RaceResult result) {
        if (result.getDnf()) {
            return DNF_PENALTY;
        }

        return PositionPoints.getPointsForPosition(result.getEndPosition(), RaceTypeEnum.QUALIFYING);
    }
}
