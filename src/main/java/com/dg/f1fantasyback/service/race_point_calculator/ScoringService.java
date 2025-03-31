package com.dg.f1fantasyback.service.race_point_calculator;

import com.dg.f1fantasyback.model.entity.RaceResult;
import com.dg.f1fantasyback.model.enums.RaceTypeEnum;
import org.springframework.stereotype.Service;

@Service
public class ScoringService {

    /**
     * Retourne le nombre de point "Fantasy" d'un pilote
     */
    public int computePoints(RaceResult result) {
        RaceTypeEnum type = result.getRace().getType();
        ScoringStrategy strategy = ScoringStrategyFactory.getStrategy(type);
        return strategy.calculatePoints(result);
    }
}
