package com.dg.f1fantasyback.service.race_point_calculator;

import com.dg.f1fantasyback.model.entity.RaceResult;
import com.dg.f1fantasyback.model.enums.RaceTypeEnum;

public class GrandPrixScoringStrategy implements ScoringStrategy {
    private static final int DNF_PENALTY = -20;
    private static final int FASTEST_LAP_POINT = 10;
    private static final int DRIVER_OF_THE_DAY = 10;
    private static final int POINT_LOST_BY_POSITION_LOST = 1;
    private static final int POINT_EARN_BY_POSITION_GAINED = 1;
    private static final int POINT_EARN_BY_OVERTAKE = 1;

    @Override
    public int calculatePoints(RaceResult result) {
        if (result.getDnf()) {
            return DNF_PENALTY;
        }

        int points = 0;

        if (result.getFastestLap()) {
            points += FASTEST_LAP_POINT;
        }

        if (result.getDriverOfTheDay()) {
            points += DRIVER_OF_THE_DAY;
        }

        points += PositionPoints.getPointsForPosition(result.getEndPosition(), RaceTypeEnum.QUALIFYING);

        int positionDiff = result.getEndPosition() - result.getStartPosition();

        if (positionDiff > 0) {
            points -= positionDiff * POINT_LOST_BY_POSITION_LOST;
        } else {
            points += positionDiff * POINT_EARN_BY_POSITION_GAINED;
        }

        points += result.getNbOvertakes() * POINT_EARN_BY_OVERTAKE;

        return points;
    }
}
