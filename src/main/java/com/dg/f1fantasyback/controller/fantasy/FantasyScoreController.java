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
    public Integer getConstructorPointsOnRace(@PathVariable Integer eventId, @PathVariable Integer constructorId) {
        return fantasyScoreService.getConstructorPointsOnRace(constructorId, eventId);
    }

    @GetMapping("/constructors/{constructorId}")
    public Integer getConstructorPoints(@PathVariable Integer constructorId) {
        return fantasyScoreService.getConstructorPoints(constructorId);
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
    public List<Map<String, String>> getConstructorLeaderboard() {
        return fantasyScoreService.getConstructorLeaderboard();
    }

    @GetMapping("/leaderboard/drivers")
    public List<Map<String, String>> getDriverLeaderboard() {
        return fantasyScoreService.getDriverLeaderboard();
    }

    @GetMapping("/events/{eventId}")
    public FantasyScoreService.EventPoints getScoreOnEvent(@PathVariable Integer eventId) {
        return fantasyScoreService.getScoreOnEvent(eventId);
    }

    @GetMapping("/gp/{gpId}")
    public FantasyScoreService.EventPoints getScoreOnGp(@PathVariable Integer gpId) {
        return fantasyScoreService.getScoreOnGp(gpId);
    }

    @GetMapping("/events/{eventId}/team/{teamId}")
    public Map<String, Object> getTeamScoreOnRace(@PathVariable Integer eventId, @PathVariable Integer teamId) {
        return fantasyScoreService.getTeamScoreOnRace(teamId, eventId);
    }
}
