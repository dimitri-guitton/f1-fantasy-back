package com.dg.f1fantasyback.service;

import com.dg.f1fantasyback.model.entity.fantasy.FantasyScore;
import com.dg.f1fantasyback.model.entity.racing.Constructor;
import com.dg.f1fantasyback.model.entity.racing.Driver;
import com.dg.f1fantasyback.model.entity.racing.Event;
import com.dg.f1fantasyback.model.entity.racing.GrandPrix;
import com.dg.f1fantasyback.repository.FantasyRacePointRepository;
import com.dg.f1fantasyback.repository.GrandPrixRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class FantasyScoreService {
    public record EventPoints(List<DriverPoint> driverPoints, List<ConstructorPoint> constructorPoints) {

    }

    public record DriverPoint(Integer driverId, String driverName, Integer value) {
    }

    public record ConstructorPoint(Integer constructorId, String constructorName, Integer value) {
    }

    private final FantasyRacePointRepository fantasyRacePointRepository;
    private final GrandPrixRepository grandPrixRepository;

    public FantasyScoreService(FantasyRacePointRepository fantasyRacePointRepository, GrandPrixRepository grandPrixRepository) {
        this.fantasyRacePointRepository = fantasyRacePointRepository;
        this.grandPrixRepository = grandPrixRepository;
    }

    public Integer getConstructorPointsOnRace(Integer teamId, Integer raceId) {
        Optional<FantasyScore> result = fantasyRacePointRepository.findFirstByConstructor_IdAndEvent_Id(teamId, raceId);

        if (result.isPresent()) {
            return result.get().getPoints();
        } else {
            return 0;
        }
    }

    public Integer getConstructorPoints(Integer teamId) {
        List<FantasyScore> results = fantasyRacePointRepository.findByConstructor_Id(teamId);
        Integer totalPoints = 0;
        for (FantasyScore result : results) {
            totalPoints += result.getPoints();
        }

        return totalPoints;

    }

    public Integer getDriverPointOnRace(Integer driverId, Integer raceId) {
        Optional<FantasyScore> result = fantasyRacePointRepository.findFirstByDriver_IdAndEvent_Id(driverId, raceId);

        if (result.isPresent()) {
            return result.get().getPoints();
        } else {
            return 0;
        }
    }

    public Integer getDriverPoints(Integer driverId) {
        List<FantasyScore> results = fantasyRacePointRepository.findByDriver_Id((driverId));
        Integer totalPoints = 0;
        for (FantasyScore result : results) {
            totalPoints += result.getPoints();
        }

        return totalPoints;

    }

    public List<Map<String, String>> getConstructorLeaderboard() {
        List<Object[]> result = fantasyRacePointRepository.findTeamLeaderboard();
        List<Map<String, String>> leaderboard = new ArrayList<>();

        for (Object[] row : result) {
            Map<String, String> map = new HashMap<>();
            map.put("team_id", String.valueOf(row[0]));
            map.put("name", String.valueOf(row[1]));
            map.put("point", String.valueOf(row[2]));
            leaderboard.add(map);
        }
        return leaderboard;
    }

    public List<Map<String, String>> getDriverLeaderboard() {
        List<Object[]> result = fantasyRacePointRepository.findDriverLeaderboard();

        return result.stream()
                     .map(r -> Map.ofEntries(
                             Map.entry("drvier_id", String.valueOf(r[0])),
                             Map.entry("name", String.valueOf(r[1])),
                             Map.entry("value", String.valueOf(r[2]))
                                            ))
                     .sorted(Comparator.comparingInt(i -> -Integer.parseInt(i.get("value"))))
                     .collect(Collectors.toList());
    }

    public void createDriverPoint(Event event, Driver driver, int points) {
        FantasyScore racePoint = FantasyScore.builder().event(event).driver(driver).points(points).build();

        fantasyRacePointRepository.save(racePoint);
    }

    public void createConstructorPoint(Event event, Constructor constructor, int points) {
        FantasyScore racePoint = FantasyScore.builder().event(event).constructor(constructor).points(points).build();

        fantasyRacePointRepository.save(racePoint);
    }

    public Map<String, Object> getTeamScoreOnRace(Integer teamId, Integer eventId) {
        return null;
    }

    public EventPoints getScoreOnEvent(Integer eventId) {
        List<FantasyScore> fantasyScores = fantasyRacePointRepository.findByEvent_Id(eventId);

        List<DriverPoint> driverPoints = new ArrayList<>();
        List<ConstructorPoint> constructorPoints = new ArrayList<>();

        for (FantasyScore fantasyScore : fantasyScores) {
            if (fantasyScore.getDriver() != null) {
                driverPoints.add(new DriverPoint(
                        fantasyScore.getDriver().getId(),
                        fantasyScore.getDriver().getFullName(),
                        fantasyScore.getPoints()
                ));
            } else if (fantasyScore.getConstructor() != null) {
                constructorPoints.add(new ConstructorPoint(
                        fantasyScore.getConstructor().getId(),
                        fantasyScore.getConstructor().getName(),
                        fantasyScore.getPoints()
                ));
            }
        }

        driverPoints.sort(Comparator.comparing(DriverPoint::value).reversed());
        constructorPoints.sort(Comparator.comparing(ConstructorPoint::value).reversed());

        return new EventPoints(driverPoints, constructorPoints);
    }

    public EventPoints getScoreOnGp(Integer gpId) {
        Map<String, Object> result = new HashMap<>();
        Optional<GrandPrix> optGrandPrix = grandPrixRepository.findById(gpId);

        if (optGrandPrix.isEmpty()) {
            throw new IllegalArgumentException("Grand Prix not found");
        }
        GrandPrix grandPrix = optGrandPrix.get();

        Set<Event> events = grandPrix.getEvents();
        Map<Integer, DriverPoint> driverPoints = new HashMap<>();
        Map<Integer, ConstructorPoint> constructorPoints = new HashMap<>();

        for (Event event : events) {
            EventPoints eventPoints = getScoreOnEvent(event.getId());
            for (DriverPoint points : eventPoints.driverPoints) {
                driverPoints.computeIfPresent(points.driverId, (k, v) -> new DriverPoint(k, v.driverName, v.value + points.value));
                driverPoints.putIfAbsent(points.driverId, points);
            }

            for (ConstructorPoint points : eventPoints.constructorPoints) {
                constructorPoints.computeIfPresent(points.constructorId, (k, v) -> new ConstructorPoint(k, v.constructorName, v.value + points.value));
                constructorPoints.putIfAbsent(points.constructorId, points);
            }
        }

        List<DriverPoint> driverPointList = driverPoints.values().stream().sorted(Comparator.comparing(DriverPoint::value).reversed()).toList();
        List<ConstructorPoint> constructorPointsList = constructorPoints.values()
                                                                        .stream()
                                                                        .sorted(Comparator.comparing(ConstructorPoint::value).reversed())
                                                                        .toList()
                ;

        return new EventPoints(driverPointList, constructorPointsList);
    }
}
