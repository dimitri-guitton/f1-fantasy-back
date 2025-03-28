package com.dg.f1fantasyback.service.race_point_calculator;

import com.dg.f1fantasyback.model.entity.FantasyRacePoint;
import com.dg.f1fantasyback.model.entity.Race;
import com.dg.f1fantasyback.model.entity.RaceResult;
import com.dg.f1fantasyback.model.enums.RaceTypeEnum;
import com.dg.f1fantasyback.repository.FantasyRacePointRepository;
import com.dg.f1fantasyback.service.RaceResultService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RaceCalculator {

    private final RaceResultService raceResultService;
    private final FantasyRacePointRepository fantasyRacePointRepository;

    public RaceCalculator(RaceResultService raceResultService, FantasyRacePointRepository fantasyRacePointRepository) {
        this.raceResultService = raceResultService;
        this.fantasyRacePointRepository = fantasyRacePointRepository;
    }

    public void calculateRacePoints(Race race) {
        Iterable<RaceResult> results = raceResultService.getResultForRace(race.getId());

        BaseRacePointCalculator calculator;

        if (race.getType() == RaceTypeEnum.GP) {
            calculator = new GpPointCalculator();
        } else if (race.getType() == RaceTypeEnum.QUALIFYING) {
            calculator = new QualifyingPointCalculator();
        } else {
            throw new RuntimeException("This race type is not supported");
        }

        Map<Integer, FantasyRacePoint> userPoints = new HashMap<>();
        Map<Integer, FantasyRacePoint> teamPoints = new HashMap<>();

        results.forEach(result -> {
            Integer point = calculator.calculatePoints(result.getPosition(), result.getDnf());
            FantasyRacePoint userPoint = userPoints.getOrDefault(result.getDriver().getId(), new FantasyRacePoint());
            userPoint.setPoint(point);
            userPoint.setRace(race);
            userPoint.setDriver(result.getDriver());
            userPoints.put(result.getDriver().getId(), userPoint);

            FantasyRacePoint teamPoint = teamPoints.getOrDefault(result.getDriver().getTeam().getId(), new FantasyRacePoint());
            Integer currentTeamPoint = teamPoint.getPoint() == null ? 0 : teamPoint.getPoint();
            teamPoint.setPoint(currentTeamPoint + point);
            teamPoint.setTeam(result.getDriver().getTeam());
            teamPoint.setRace(race);
            teamPoints.put(result.getDriver().getTeam().getId(), teamPoint);
        });

        fantasyRacePointRepository.saveAll(userPoints.values());
        fantasyRacePointRepository.saveAll(teamPoints.values());
    }
}
