package com.dg.f1fantasyback.service.scoring;

import com.dg.f1fantasyback.model.entity.racing.EventResult;
import com.dg.f1fantasyback.model.enums.EventType;
import org.springframework.stereotype.Service;

@Service
public class SprintScoringStrategy implements ScoringStrategy {
    private static final int DNF_PENALTY = -20;
    private static final int FASTEST_LAP_POINT = 5;
    private static final int POINT_LOST_BY_POSITION_LOST = 1;
    private static final int POINT_EARN_BY_POSITION_GAINED = 1;
    private static final int POINT_EARN_BY_OVERTAKE = 1;

    @Override
    public int calculateDriverPoints(EventResult result) {
        if (result.getDnf()) {
            return DNF_PENALTY;
        }

        int points = 0;

        if (result.getFastestLap()) {
            points += FASTEST_LAP_POINT;
        }

        points += PositionPoints.getPointsForPosition(result.getEndPosition(), EventType.QUALIFYING);

        int positionDiff = result.getEndPosition() - result.getStartPosition();

        if (positionDiff > 0) {
            points -= positionDiff * POINT_LOST_BY_POSITION_LOST;
        } else {
            points += positionDiff * POINT_EARN_BY_POSITION_GAINED;
        }

        points += result.getNbOvertakes() * POINT_EARN_BY_OVERTAKE;

        return points;
    }

    @Override
    public int calculateTeamPoints(EventResult resultDriver1, EventResult resultDriver2) {
        return calculateDriverPoints(resultDriver1) + calculateDriverPoints(resultDriver2);
    }

}