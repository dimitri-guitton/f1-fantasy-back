package com.dg.f1fantasyback.service.scoring;

import com.dg.f1fantasyback.model.enums.EventType;

public enum PositionPoints {
    P1(25, 8, 10),
    P2(18, 7, 9),
    P3(15, 6, 8),
    P4(12, 5, 7),
    P5(10, 4, 6),
    P6(8, 3, 5),
    P7(6, 2, 4),
    P8(4, 1, 3),
    P9(2, 0, 2),
    P10(1, 0, 1);

    private final int coursePoints;
    private final int sprintPoints;
    private final int qualifPoints;

    PositionPoints(int coursePoints, int sprintPoints, int qualifPoints) {
        this.coursePoints = coursePoints;
        this.sprintPoints = sprintPoints;
        this.qualifPoints = qualifPoints;
    }

    public static int getPointsForPosition(int position, EventType raceType) {
        return switch (position) {
            case 1 -> P1.getPoints(raceType);
            case 2 -> P2.getPoints(raceType);
            case 3 -> P3.getPoints(raceType);
            case 4 -> P4.getPoints(raceType);
            case 5 -> P5.getPoints(raceType);
            case 6 -> P6.getPoints(raceType);
            case 7 -> P7.getPoints(raceType);
            case 8 -> P8.getPoints(raceType);
            case 9 -> P9.getPoints(raceType);
            case 10 -> P10.getPoints(raceType);
            default -> 0;
        };
    }

    private int getPoints(EventType raceType) {
        if (raceType == EventType.QUALIFYING) {
            return qualifPoints;
        } else if (raceType == EventType.GP) {
            return coursePoints;
        } else if (raceType == EventType.SPRINT) {
            return sprintPoints;
        }

        return 0;
    }
}


