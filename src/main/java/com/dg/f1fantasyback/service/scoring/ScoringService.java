package com.dg.f1fantasyback.service.scoring;

import com.dg.f1fantasyback.exception.PointCalculatorException;
import com.dg.f1fantasyback.model.entity.Race;
import com.dg.f1fantasyback.model.entity.RaceResult;
import com.dg.f1fantasyback.model.entity.Team;
import com.dg.f1fantasyback.model.enums.RaceTypeEnum;
import com.dg.f1fantasyback.repository.RaceRepository;
import com.dg.f1fantasyback.repository.RaceResultRepository;
import com.dg.f1fantasyback.service.FantasyRacePointService;
import com.dg.f1fantasyback.service.RaceResultService;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ScoringService {

    private final RaceResultService raceResultService;
    private final FantasyRacePointService fantasyRacePointService;
    private final RaceResultRepository raceResultRepository;
    private final RaceRepository raceRepository;

    public ScoringService(RaceResultService raceResultService, FantasyRacePointService fantasyRacePointService, RaceResultRepository raceResultRepository, RaceRepository raceRepository) {
        this.raceResultService = raceResultService;
        this.fantasyRacePointService = fantasyRacePointService;
        this.raceResultRepository = raceResultRepository;
        this.raceRepository = raceRepository;
    }

    /**
     * Retourne le nombre de point "Fantasy" d'un pilote
     */
    private int computeDriverPoints(RaceResult result) {
        RaceTypeEnum type = result.getRace().getType();
        ScoringStrategy strategy = ScoringStrategyFactory.getStrategy(type);
        return strategy.calculateDriverPoints(result);
    }

    /**
     * Retourne le nombre de point "Fantasy" d'une écurie
     */
    private int computeTeamPoints(RaceResult resultDriver1, RaceResult resultDriver2) {
        RaceTypeEnum type = resultDriver1.getRace().getType();
        ScoringStrategy strategy = ScoringStrategyFactory.getStrategy(type);
        return strategy.calculateTeamPoints(resultDriver1, resultDriver2);
    }

    public void computeRacePoints(Race race) {
        if (race.getIsCalculated()) {
            throw new PointCalculatorException("This race has already been calculated");
        }

        if (!EnumSet.of(RaceTypeEnum.GP, RaceTypeEnum.QUALIFYING, RaceTypeEnum.SPRINT).contains(race.getType())) {
            throw new PointCalculatorException("This race type is not supported");
        }

        Iterable<RaceResult> results = raceResultService.getResultForRace(race.getId());
        results.forEach(result -> {
            int points = computeDriverPoints(result);
            fantasyRacePointService.createDriverPoint(race, result.getDriver(), points);
        });

        Map<Team, List<RaceResult>> resultsGroupedByTeam = StreamSupport.stream(results.spliterator(), false)
                                                                        .collect(Collectors.groupingBy(rr -> rr.getDriver().getTeam()));

        for (var entry : resultsGroupedByTeam.entrySet()) {
            int points = computeTeamPoints(entry.getValue().get(0), entry.getValue().get(1));
            fantasyRacePointService.createTeamPoint(race, entry.getKey(), points);
        }

        race.setIsCalculated(true);
        raceRepository.saveAndFlush(race);
    }

    public void computeUncalculedRaces() {
        List<Race> races = raceRepository.findAllByIsCalculatedOrderByTypeAsc(false);

        races.forEach(this::computeRacePoints);
    }
}
