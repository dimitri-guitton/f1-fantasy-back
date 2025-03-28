package com.dg.f1fantasyback.service.race_point_calculator;

import com.dg.f1fantasyback.model.enums.RaceTypeEnum;
import org.springframework.stereotype.Service;

@Service
public class QualifyingPointCalculator implements BaseRacePointCalculator {
    private static final int DNF_PENALTY = -5;

    @Override
    public Integer calculatePoints(Integer position, Boolean isDnf) {
        if (isDnf) {
            return DNF_PENALTY;
        } else {
            return PositionPoints.getPointsForPosition(position, RaceTypeEnum.QUALIFYING);
        }
    }
}
