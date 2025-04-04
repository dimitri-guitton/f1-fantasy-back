package com.dg.f1fantasyback.controller.fantasy;

import com.dg.f1fantasyback.model.dto.fantasy_team.FantasyTeamCreateDto;
import com.dg.f1fantasyback.model.dto.fantasy_team.FantasyTeamDetailDto;
import com.dg.f1fantasyback.model.dto.fantasy_team.FantasyTeamDto;
import com.dg.f1fantasyback.model.entity.AppUser;
import com.dg.f1fantasyback.security.AuthService;
import com.dg.f1fantasyback.service.FantasyTeamService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Iterable<FantasyTeamDto> getFantasyTeams() {
        return fantasyTeamService.getTeams();
    }

    @GetMapping("/my")
    public List<FantasyTeamDetailDto> getUserTeams(){
        AppUser currentUser = authService.getAuthenticatedUser();
        return fantasyTeamService.getUserTeams(currentUser);
    }

    @GetMapping("/{id}")
    public FantasyTeamDetailDto getFantasyTeamById(@PathVariable Long id) {
        return fantasyTeamService.getTeamById(id);
    }

    @GetMapping("/{id}/points/events/{eventId}")
    public Object getFantasyTeamPointsOnRace(@PathVariable Long id, @PathVariable Integer eventId) {
        return fantasyTeamService.getFantasyTeamPointsOnRace(id, eventId);
    }

    @PostMapping
    public FantasyTeamDetailDto createFantasyTeam(@RequestBody FantasyTeamCreateDto fantasyTeamCreateDto) {
        return fantasyTeamService.createTeam(authService.getAuthenticatedUser(), fantasyTeamCreateDto);
    }

    @PutMapping("/{id}")
    public FantasyTeamDetailDto updateFantasyTeam(@PathVariable Long id, @RequestBody FantasyTeamCreateDto fantasyTeamCreateDto) {
        return fantasyTeamService.updateTeam(id, fantasyTeamCreateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteFantasyTeam(@PathVariable Long id) {
        fantasyTeamService.deleteTeam(id);
    }
}
