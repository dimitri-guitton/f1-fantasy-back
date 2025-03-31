package com.dg.f1fantasyback.service;

import com.dg.f1fantasyback.model.entity.fantasy.FantasyScore;
import com.dg.f1fantasyback.model.entity.racing.Constructor;
import com.dg.f1fantasyback.model.entity.racing.Driver;
import com.dg.f1fantasyback.model.entity.racing.Event;
import com.dg.f1fantasyback.repository.FantasyRacePointRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FantasyScoreService {

    private final FantasyRacePointRepository fantasyRacePointRepository;

    public FantasyScoreService(FantasyRacePointRepository fantasyRacePointRepository) {
        this.fantasyRacePointRepository = fantasyRacePointRepository;
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
        List<Map<String, String>> leaderboard = new ArrayList<>();

        for (Object[] row : result) {
            Map<String, String> map = new HashMap<>();
            map.put("drvier_id", String.valueOf(row[0]));
            map.put("name", String.valueOf(row[1]));
            map.put("point", String.valueOf(row[2]));
            leaderboard.add(map);
        }
        return leaderboard;
    }

    public void createDriverPoint(Event event, Driver driver, int points) {
        FantasyScore racePoint = FantasyScore.builder().event(event).driver(driver).points(points).build();

        fantasyRacePointRepository.save(racePoint);
    }

    public void createConstructorPoint(Event event, Constructor constructor, int points) {
        FantasyScore racePoint = FantasyScore.builder().event(event).constructor(constructor).points(points).build();

        fantasyRacePointRepository.save(racePoint);
    }
}
