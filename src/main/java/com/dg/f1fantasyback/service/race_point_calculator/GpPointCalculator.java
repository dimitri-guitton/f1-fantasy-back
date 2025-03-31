package com.dg.f1fantasyback.service.race_point_calculator;

import com.dg.f1fantasyback.model.enums.RaceTypeEnum;
import org.springframework.stereotype.Service;

@Service
public class GpPointCalculator {
    private static final int DNF_PENALTY = -20;
    private static final int FASTEST_LAP = 10;
    private static final int DRIVER_OF_THE_DAY = 10;
    private static final int POINT_LOST_BY_POSITION_LOST = 1;
    private static final int POINT_EARN_BY_POSITION_GAINED = 1;
    private static final int POINT_EARN_BY_OVERTAKE = 1;

    public int calculatePoints(Integer startPosition, Integer endPosition, Integer nbOvertakes, Boolean isDnf, Boolean fastestLap, Boolean driverOfTheDay) {
        int points = 0;

        if (isDnf) {
            return DNF_PENALTY;
        }

        if (fastestLap) {
            points += FASTEST_LAP;
        }

        if (driverOfTheDay) {
            points += DRIVER_OF_THE_DAY;
        }

        points += PositionPoints.getPointsForPosition(endPosition, RaceTypeEnum.QUALIFYING);

        int positionDiff = endPosition - startPosition;

        if (positionDiff > 0) {
            points -= positionDiff * POINT_LOST_BY_POSITION_LOST;
        } else {
            points += positionDiff * POINT_EARN_BY_POSITION_GAINED;
        }

        points += nbOvertakes * POINT_EARN_BY_OVERTAKE;

        return points;
    }
}
