package com.dg.f1fantasyback.service.race_point_calculator;

import com.dg.f1fantasyback.model.enums.RaceTypeEnum;

public enum PositionPoints {
    P1(25, 10),
    P2(18, 9),
    P3(15, 8),
    P4(12, 7),
    P5(10, 6),
    P6(8, 5),
    P7(6, 4),
    P8(4, 3),
    P9(2, 2),
    P10(1, 1);

    private final int coursePoints;
    private final int qualifPoints;

    PositionPoints(int coursePoints, int qualifPoints) {
        this.coursePoints = coursePoints;
        this.qualifPoints = qualifPoints;
    }

    public int getPoints(RaceTypeEnum raceType) {
        if (raceType == RaceTypeEnum.QUALIFYING) {
            return qualifPoints;
        } else if (raceType == RaceTypeEnum.GP) {
            return coursePoints;
        }

        return 0;
    }

    public static int getPointsForPosition(int position, RaceTypeEnum raceType) {
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
}


