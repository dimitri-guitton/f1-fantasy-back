package com.dg.f1fantasyback.controller;

import com.dg.f1fantasyback.model.dto.user_team.UserTeamCreateDto;
import com.dg.f1fantasyback.model.dto.user_team.UserTeamDetailDto;
import com.dg.f1fantasyback.model.dto.user_team.UserTeamDto;
import com.dg.f1fantasyback.security.AuthService;
import com.dg.f1fantasyback.service.UserTeamService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-teams")
public class UserTeamController {
    private final UserTeamService userTeamService;
    private final AuthService authService;

    public UserTeamController(UserTeamService userTeamService, AuthService authService) {
        this.userTeamService = userTeamService;
        this.authService = authService;
    }

    @GetMapping
    public Iterable<UserTeamDto> getUserTeams() {
        return userTeamService.getTeams();
    }

    @GetMapping("/{id}")
    public UserTeamDetailDto getUserTeamById(@PathVariable Long id) {
        return userTeamService.getTeamById(id);
    }

    @GetMapping("/{teamId}/points/{raceId}/race")
    public Object getUserTeamPointsOnRace(@PathVariable Long teamId, @PathVariable Integer raceId){
        return userTeamService.getUserTeamPointsOnRace(teamId, raceId);
    }


    @PostMapping
    public UserTeamDetailDto createUserTeam(@RequestBody UserTeamCreateDto userTeamCreateDto) {
        return userTeamService.createTeam(authService.getAuthenticatedUser(), userTeamCreateDto);
    }

    @PutMapping("/{id}")
    public UserTeamDetailDto updateUserTeam(@PathVariable Long id, @RequestBody UserTeamCreateDto userTeamCreateDto) {
        return userTeamService.updateTeam(id, userTeamCreateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUserTeam(@PathVariable Long id) {
        userTeamService.deleteTeam(id);
    }
}
