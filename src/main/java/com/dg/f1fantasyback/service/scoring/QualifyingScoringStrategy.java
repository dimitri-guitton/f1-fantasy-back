package com.dg.f1fantasyback.service.scoring;

import com.dg.f1fantasyback.model.entity.RaceResult;
import com.dg.f1fantasyback.model.enums.RaceTypeEnum;
import org.springframework.stereotype.Service;

@Service
public class QualifyingScoringStrategy implements ScoringStrategy {
    private static final int DNF_PENALTY = -5;

    @Override
    public int calculateDriverPoints(RaceResult result) {
        if (result.getDnf()) {
            return DNF_PENALTY;
        }

        return PositionPoints.getPointsForPosition(result.getEndPosition(), RaceTypeEnum.QUALIFYING);
    }

    @Override
    public int calculateTeamPoints(RaceResult resultDriver1, RaceResult resultDriver2) {
        int posDriver1 = resultDriver1.getEndPosition();
        int posDriver2 = resultDriver2.getEndPosition();

        int points = calculateDriverPoints(resultDriver1) + calculateDriverPoints(resultDriver2);

        int additionalPoints = -1;
        if (hasReachedQ3(posDriver1) && hasReachedQ3(posDriver2)) {
            additionalPoints = 10;
        }

        if (hasReachedQ3(posDriver1) || hasReachedQ3(posDriver2)) {
            additionalPoints = 5;
        }

        if (hasReachedQ2(posDriver1) && hasReachedQ2(posDriver2)) {
            additionalPoints = 3;
        }

        if (hasReachedQ2(posDriver1) || hasReachedQ2(posDriver2)) {
            additionalPoints = 1;
        }

        return points + additionalPoints;

    }

    private boolean hasReachedQ2(int position) {
        return position <= 15;
    }

    private boolean hasReachedQ3(int position) {
        return position <= 15;
    }
}
