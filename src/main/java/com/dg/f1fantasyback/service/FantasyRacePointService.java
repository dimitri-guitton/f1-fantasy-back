package com.dg.f1fantasyback.service;

import com.dg.f1fantasyback.model.entity.FantasyRacePoint;
import com.dg.f1fantasyback.repository.FantasyRacePointRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
}
