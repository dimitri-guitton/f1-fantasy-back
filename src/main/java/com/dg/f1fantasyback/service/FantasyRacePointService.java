package com.dg.f1fantasyback.service;

import com.dg.f1fantasyback.model.entity.FantasyRacePoint;
import com.dg.f1fantasyback.repository.FantasyRacePointRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FantasyRacePointService {

    private final FantasyRacePointRepository fantasyRacePointRepository;

    public FantasyRacePointService(FantasyRacePointRepository fantasyRacePointRepository) {
        this.fantasyRacePointRepository = fantasyRacePointRepository;
    }

    public Integer getTeamPointOnRace(Integer teamId, Integer raceId) {
        Optional<FantasyRacePoint> result = fantasyRacePointRepository.findFirstByTeam_IdAndRace_Id(teamId, raceId);

        if (result.isPresent()) {
            return result.get().getPoint();
        } else {
            return 0;
        }
    }

    public Integer getTeamPoints(Integer teamId) {
        List<FantasyRacePoint> results = fantasyRacePointRepository.findByTeam_Id(teamId);
        Integer totalPoints = 0;
        for (FantasyRacePoint result : results) {
            totalPoints += result.getPoint();
        }

        return totalPoints;

    }

    public Integer getDriverPointOnRace(Integer driverId, Integer raceId) {
        Optional<FantasyRacePoint> result = fantasyRacePointRepository.findFirstByDriver_IdAndRace_Id(driverId, raceId);

        if (result.isPresent()) {
            return result.get().getPoint();
        } else {
            return 0;
        }
    }

    public Integer getDriverPoints(Integer driverId) {
        List<FantasyRacePoint> results = fantasyRacePointRepository.findByDriver_Id((driverId));
        Integer totalPoints = 0;
        for (FantasyRacePoint result : results) {
            totalPoints += result.getPoint();
        }

        return totalPoints;

    }

    public List<Map<String, String>> getTeamLeaderboard() {
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
}
