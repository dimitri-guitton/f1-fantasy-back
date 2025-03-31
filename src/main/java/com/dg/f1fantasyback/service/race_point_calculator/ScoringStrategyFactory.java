package com.dg.f1fantasyback.service.race_point_calculator;

import com.dg.f1fantasyback.model.enums.RaceTypeEnum;

public class ScoringStrategyFactory {
    public static ScoringStrategy getStrategy(RaceTypeEnum eventType) {
        return switch (eventType) {
            case QUALIFYING -> new QualifyingScoringStrategy();
            case GP -> new GrandPrixScoringStrategy();
            case SPRINT -> new SprintScoringStrategy();
            default -> throw new IllegalArgumentException("Type d'épreuve non supporté");
        };
    }
}
