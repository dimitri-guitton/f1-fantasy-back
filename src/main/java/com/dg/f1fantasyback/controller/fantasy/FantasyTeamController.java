package com.dg.f1fantasyback.controller.fantasy;

import com.dg.f1fantasyback.model.dto.fantasy_team.FantasyTeamCreateDto;
import com.dg.f1fantasyback.model.dto.fantasy_team.FantasyTeamDetailDto;
import com.dg.f1fantasyback.model.dto.fantasy_team.FantasyTeamDto;
import com.dg.f1fantasyback.security.AuthService;
import com.dg.f1fantasyback.service.FantasyTeamService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fantasy-teams")
public class FantasyTeamController {
    private final FantasyTeamService fantasyTeamService;
    private final AuthService authService;

    public FantasyTeamController(FantasyTeamService fantasyTeamService, AuthService authService) {
        this.fantasyTeamService = fantasyTeamService;
        this.authService = authService;
    }

    @GetMapping
    public Iterable<FantasyTeamDto> getUserTeams() {
        return fantasyTeamService.getTeams();
    }

    @GetMapping("/{id}")
    public FantasyTeamDetailDto getUserTeamById(@PathVariable Long id) {
        return fantasyTeamService.getTeamById(id);
    }

    @GetMapping("/{id}/points/events/{eventId}")
    public Object getUserTeamPointsOnRace(@PathVariable Long id, @PathVariable Integer eventId) {
        return fantasyTeamService.getUserTeamPointsOnRace(id, eventId);
    }


    @PostMapping
    public FantasyTeamDetailDto createUserTeam(@RequestBody FantasyTeamCreateDto fantasyTeamCreateDto) {
        return fantasyTeamService.createTeam(authService.getAuthenticatedUser(), fantasyTeamCreateDto);
    }

    @PutMapping("/{id}")
    public FantasyTeamDetailDto updateUserTeam(@PathVariable Long id, @RequestBody FantasyTeamCreateDto fantasyTeamCreateDto) {
        return fantasyTeamService.updateTeam(id, fantasyTeamCreateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUserTeam(@PathVariable Long id) {
        fantasyTeamService.deleteTeam(id);
    }
}
