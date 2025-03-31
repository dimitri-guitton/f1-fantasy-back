package com.dg.f1fantasyback.service.race_point_calculator;

import com.dg.f1fantasyback.exception.PointCalculatorException;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RaceCalculator {

    private final RaceResultService raceResultService;
    private final FantasyRacePointRepository fantasyRacePointRepository;
    private final RaceResultRepository raceResultRepository;
    private final RaceRepository raceRepository;

    public RaceCalculator(RaceResultService raceResultService,
                          FantasyRacePointRepository fantasyRacePointRepository,
                          RaceResultRepository raceResultRepository,
                          RaceRepository raceRepository) {
        this.raceResultService = raceResultService;
        this.fantasyRacePointRepository = fantasyRacePointRepository;
        this.raceResultRepository = raceResultRepository;
        this.raceRepository = raceRepository;
    }

    public void calculateRacePoints(Race race) {
        if (race.getIsCalculated()) {
            throw new PointCalculatorException("This race has already been calculated");
        }

        if (race.getType() != RaceTypeEnum.GP && race.getType() != RaceTypeEnum.QUALIFYING) {
            throw new PointCalculatorException("This race type is not supported");
        }

        Optional<Race> qualifyingRace;
        Map<Integer, Integer> qualifyingResults;
        if (race.getType() == RaceTypeEnum.GP) {
            qualifyingRace = raceRepository.findByTypeAndGrandPrix_Id(RaceTypeEnum.QUALIFYING, race.getGrandPrix()
                                                                                                   .getId());

            if (qualifyingRace.isEmpty() || !qualifyingRace.get().getIsCalculated()) {
                throw new PointCalculatorException("The race need to calculate qualifying race before");
            }

            List<Object[]> positions = raceResultRepository.getPositionsByDriver(qualifyingRace.get().getId());
            qualifyingResults = positions.stream()
                                         .collect(Collectors.toMap(
                                                 item -> (Integer) item[0],
                                                 item -> (Integer) item[1],
                                                 (existing, replacement) -> existing,
                                                 HashMap::new
                                                                  ));
        } else {
            qualifyingResults = new HashMap<>();
        }

        Iterable<RaceResult> results = raceResultService.getResultForRace(race.getId());

        GpPointCalculator gpPointCalculator = new GpPointCalculator();
        QualifyingPointCalculator qualifyingPointCalculator = new QualifyingPointCalculator();

        Map<Integer, FantasyRacePoint> userPoints = new HashMap<>();
        Map<Integer, FantasyRacePoint> teamPoints = new HashMap<>();

        results.forEach(result -> {
            Integer point = 0;
            if (race.getType() == RaceTypeEnum.GP) {
                Integer startPosition = qualifyingResults.getOrDefault(result.getDriver().getId(), 0);

                point = gpPointCalculator.calculatePoints(startPosition,
                                                          result.getEndPosition(),
                                                          result.getNbOvertakes(),
                                                          result.getDnf(),
                                                          result.getFastestLap(),
                                                          result.getDriverOfTheDay());
            } else if (race.getType() == RaceTypeEnum.QUALIFYING) {
                point = qualifyingPointCalculator.calculatePoints(result.getEndPosition(), result.getDnf());
            }

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

        race.setIsCalculated(true);
        raceRepository.save(race);
    }

    public void calculateUncalculedRace() {
        List<Race> races = raceRepository.findAllByIsCalculatedOrderByTypeAsc(false);

        races.forEach(this::calculateRacePoints);
    }
}
