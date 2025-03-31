package com.dg.f1fantasyback.controller.fantasy;

import com.dg.f1fantasyback.service.FantasyScoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fantasy-scores")
public class FantasyScoreController {
    private final FantasyScoreService fantasyScoreService;

    public FantasyScoreController(FantasyScoreService fantasyScoreService) {
        this.fantasyScoreService = fantasyScoreService;
    }

    @GetMapping("/events/{eventId}/constructors/{constructorId}")
    public Integer getTeamPointOnRace(@PathVariable Integer eventId, @PathVariable Integer constructorId) {
        return fantasyScoreService.getTeamPointOnRace(constructorId, eventId);
    }

    @GetMapping("/constructors/{constructorId}")
    public Integer getTeamPoints(@PathVariable Integer constructorId) {
        return fantasyScoreService.getTeamPoints(constructorId);
    }

    @GetMapping("/events/{eventId}/drivers/{driverId}")
    public Integer getDriverPointOnRace(@PathVariable Integer eventId, @PathVariable Integer driverId) {
        return fantasyScoreService.getDriverPointOnRace(driverId, eventId);
    }

    @GetMapping("/drivers/{driverId}")
    public Integer getDriverPoints(@PathVariable Integer driverId) {
        return fantasyScoreService.getDriverPoints(driverId);
    }

    @GetMapping("/leaderboard/constructors")
    public List<Map<String, String>> getTeamLeaderboard() {
        return fantasyScoreService.getTeamLeaderboard();
    }

    @GetMapping("/leaderboard/drivers")
    public List<Map<String, String>> getDriverLeaderboard() {
        return fantasyScoreService.getDriverLeaderboard();
    }
}
