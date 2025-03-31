package com.dg.f1fantasyback.service.scoring;

import com.dg.f1fantasyback.model.enums.RaceTypeEnum;
import org.springframework.stereotype.Service;

@Service
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
