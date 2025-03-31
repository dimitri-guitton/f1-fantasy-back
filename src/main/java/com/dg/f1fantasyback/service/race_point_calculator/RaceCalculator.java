package com.dg.f1fantasyback.service.race_point_calculator;

import com.dg.f1fantasyback.exception.PointCalculatorException;
import com.dg.f1fantasyback.model.entity.FantasyRacePoint;
import com.dg.f1fantasyback.model.entity.Race;
import com.dg.f1fantasyback.model.entity.RaceResult;
import com.dg.f1fantasyback.model.enums.RaceTypeEnum;
import com.dg.f1fantasyback.repository.FantasyRacePointRepository;
import com.dg.f1fantasyback.repository.RaceRepository;
import com.dg.f1fantasyback.service.RaceResultService;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RaceCalculator {

    private final RaceResultService raceResultService;
    private final FantasyRacePointRepository fantasyRacePointRepository;
    private final RaceRepository raceRepository;
    private final ScoringService scoringService;

    public RaceCalculator(RaceResultService raceResultService, FantasyRacePointRepository fantasyRacePointRepository, RaceRepository raceRepository, ScoringService scoringService) {
        this.raceResultService = raceResultService;
        this.fantasyRacePointRepository = fantasyRacePointRepository;
        this.raceRepository = raceRepository;
        this.scoringService = scoringService;
    }

    public void calculateRacePoints(Race race) {
        if (race.getIsCalculated()) {
            throw new PointCalculatorException("This race has already been calculated");
        }

        if (!EnumSet.of(RaceTypeEnum.GP, RaceTypeEnum.QUALIFYING, RaceTypeEnum.SPRINT).contains(race.getType())) {
            throw new PointCalculatorException("This race type is not supported");
        }
        Iterable<RaceResult> results = raceResultService.getResultForRace(race.getId());

        Map<Integer, FantasyRacePoint> userPoints = new HashMap<>();
        Map<Integer, FantasyRacePoint> teamPoints = new HashMap<>();

        results.forEach(result -> {
            int point = scoringService.computePoints(result);

            FantasyRacePoint userPoint = userPoints.getOrDefault(result.getDriver().getId(), new FantasyRacePoint());
            userPoint.setPoint(point);
            userPoint.setRace(race);
            userPoint.setDriver(result.getDriver());
            userPoints.put(result.getDriver().getId(), userPoint);

            FantasyRacePoint teamPoint = teamPoints.getOrDefault(result.getDriver().getTeam().getId(), new FantasyRacePoint());
            int currentTeamPoint = teamPoint.getPoint() == null ? 0 : teamPoint.getPoint();
            teamPoint.setPoint(currentTeamPoint + point);
            teamPoint.setTeam(result.getDriver().getTeam());
            teamPoint.setRace(race);
            teamPoints.put(result.getDriver().getTeam().getId(), teamPoint);
        });

        fantasyRacePointRepository.saveAll(userPoints.values());
        fantasyRacePointRepository.saveAll(teamPoints.values());

        race.setIsCalculated(true);
        raceRepository.save(race);
    }

    public void calculateUncalculedRace() {
        List<Race> races = raceRepository.findAllByIsCalculatedOrderByTypeAsc(false);

        races.forEach(this::calculateRacePoints);
    }
}
