package com.dg.f1fantasyback.repository;

import com.dg.f1fantasyback.model.entity.FantasyRacePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FantasyRacePointRepository extends JpaRepository<FantasyRacePoint, Long> {
    Optional<FantasyRacePoint> findFirstByTeam_IdAndRace_Id(Integer teamId, Integer raceId);

    Optional<FantasyRacePoint> findFirstByDriver_IdAndRace_Id(Integer driverId, Integer raceId);

    List<FantasyRacePoint> findByTeam_Id(Integer teamId);

    List<FantasyRacePoint> findByDriver_Id(Integer driverId);

    @Query("SELECT frp.driver.id,frp.driver.lastName, SUM(frp.point) as point FROM FantasyRacePoint frp WHERE frp.driver.id IS NOT NULL GROUP BY frp.driver.id, frp.driver.lastName ORDER BY point DESC")
    List<Object[]> findDriverLeaderboard();

    @Query("SELECT frp.team.id,frp.team.label, SUM(frp.point) as point FROM FantasyRacePoint frp WHERE frp.team.id IS NOT NULL GROUP BY frp.team.id, frp.team.label ORDER BY point DESC")
    List<Object[]> findTeamLeaderboard();
}