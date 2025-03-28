package com.dg.f1fantasyback.repository;

import com.dg.f1fantasyback.model.entity.FantasyRacePoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FantasyRacePointRepository extends JpaRepository<FantasyRacePoint, Long> {
    Optional<FantasyRacePoint> findFirstByTeam_IdAndRace_Id(Integer teamId, Integer raceId);

    Optional<FantasyRacePoint> findFirstByDriver_IdAndRace_Id(Integer driverId, Integer raceId);

    List<FantasyRacePoint> findByTeam_Id(Integer teamId);

    List<FantasyRacePoint> findByDriver_Id(Integer driverId);
}