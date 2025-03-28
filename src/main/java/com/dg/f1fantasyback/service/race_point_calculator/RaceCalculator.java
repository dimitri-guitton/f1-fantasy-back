package com.dg.f1fantasyback.service.race_point_calculator;

import com.dg.f1fantasyback.model.entity.FantasyRacePoint;
import com.dg.f1fantasyback.model.entity.Race;
import com.dg.f1fantasyback.model.entity.RaceResult;
import com.dg.f1fantasyback.model.enums.RaceTypeEnum;
import com.dg.f1fantasyback.repository.FantasyRacePointRepository;
import com.dg.f1fantasyback.repository.RaceRepository;
import com.dg.f1fantasyback.repository.RaceResultRepository;
import com.dg.f1fantasyback.service.RaceResultService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RaceCalculator {

    private final RaceResultService raceResultService;
    private final FantasyRacePointRepository fantasyRacePointRepository;
    private final RaceResultRepository raceResultRepository;
    private final RaceRepository raceRepository;

    public RaceCalculator(RaceResultService raceResultService, FantasyRacePointRepository fantasyRacePointRepository, RaceResultRepository raceResultRepository, RaceRepository raceRepository) {
        this.raceResultService = raceResultService;
        this.fantasyRacePointRepository = fantasyRacePointRepository;
        this.raceResultRepository = raceResultRepository;
        this.raceRepository = raceRepository;
    }

    public void calculateRacePoints(Race race) {
        if (race.getIsCalculated()) {
            throw new RuntimeException("This race has already been calculated");
        }

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

    public void calculateUncalculedRace() {
        List<Race> races = raceRepository.findAllByIsCalculated(false);

        races.forEach(this::calculateRacePoints);
    }
}
