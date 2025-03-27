package com.dg.f1fantasyback.controller;

import com.dg.f1fantasyback.model.dto.team.TeamDto;
import com.dg.f1fantasyback.service.TeamService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teams")
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public Iterable<TeamDto> getUserTeams() {
        return teamService.getTeams();
    }

    @GetMapping("/{id}")
    public TeamDto getUserTeamById(@PathVariable Integer id) {
        return teamService.getTeamById(id);
    }
}
