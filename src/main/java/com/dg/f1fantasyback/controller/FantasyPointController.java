package com.dg.f1fantasyback.controller;

import com.dg.f1fantasyback.service.FantasyRacePointService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fantasy-points")
public class FantasyPointController {
    private final FantasyRacePointService fantasyRacePointService;

    public FantasyPointController(FantasyRacePointService fantasyRacePointService) {
        this.fantasyRacePointService = fantasyRacePointService;
    }

    @GetMapping("{raceId}/race/{teamId}/team")
    public Integer getTeamPointOnRace(@PathVariable Integer raceId, @PathVariable Integer teamId) {
        return fantasyRacePointService.getTeamPointOnRace(teamId, raceId);
    }

    @GetMapping("{teamId}/team")
    public Integer getTeamPoints(@PathVariable Integer teamId) {
        return fantasyRacePointService.getTeamPoints(teamId);
    }

    @GetMapping("{raceId}/race/{driverId}/driver")
    public Integer getDriverPointOnRace(@PathVariable Integer raceId, @PathVariable Integer driverId) {
        return fantasyRacePointService.getDriverPointOnRace(driverId, raceId);
    }

    @GetMapping("{driverId}/driver")
    public Integer getDriverPoints(@PathVariable Integer driverId) {
        return fantasyRacePointService.getDriverPoints(driverId);
    }

    @GetMapping("teams/leaderboard")
    public List<Map<String, String>> getTeamLeaderboard() {
        return fantasyRacePointService.getTeamLeaderboard();
    }

    @GetMapping("drivers/leaderboard")
    public List<Map<String, String>> getDriverLeaderboard() {
        return fantasyRacePointService.getDriverLeaderboard();
    }
}
