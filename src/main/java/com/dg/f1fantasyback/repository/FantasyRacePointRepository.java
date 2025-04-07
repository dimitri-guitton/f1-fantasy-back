package com.dg.f1fantasyback.repository;

import com.dg.f1fantasyback.model.entity.fantasy.FantasyScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FantasyRacePointRepository extends JpaRepository<FantasyScore, Long> {
    Optional<FantasyScore> findFirstByConstructor_IdAndEvent_Id(Integer constructorId, Integer eventId);

    Optional<FantasyScore> findFirstByDriver_IdAndEvent_Id(Integer driverId, Integer eventId);

    List<FantasyScore> findByConstructor_Id(Integer constructorId);

    List<FantasyScore> findByDriver_Id(Integer driverId);

    @Query("SELECT frp.driver.id, frp.driver.lastName, SUM(frp.points) as point FROM FantasyScore frp WHERE frp.driver.id IS NOT NULL GROUP BY frp.driver.id, frp.driver.lastName ORDER BY point DESC")
    List<Object[]> findDriverLeaderboard();

    @Query("SELECT frp.constructor.id,frp.constructor.name, SUM(frp.points) as point FROM FantasyScore frp WHERE frp.constructor.id IS NOT NULL GROUP BY frp.constructor.id, frp.constructor.name ORDER BY point DESC")
    List<Object[]> findTeamLeaderboard();

    @Query("SELECT sum(frp.points) as points FROM FantasyScore frp WHERE frp.event.id = :raceId AND (frp.constructor.id = :teamId1 OR frp.constructor.id = :teamId2 OR frp.driver.id = :driverId1 OR frp.driver.id = :driverId2 OR frp.driver.id = :driverId3 OR frp.driver.id = :driverId4 OR frp.driver.id = :driverId5)")
    Integer getPointsOnRaceByUserTeam(int raceId, int teamId1, int teamId2, int driverId1, int driverId2, int driverId3, int driverId4, int driverId5);

    List<FantasyScore> findByEvent_Id(Integer eventId);
}