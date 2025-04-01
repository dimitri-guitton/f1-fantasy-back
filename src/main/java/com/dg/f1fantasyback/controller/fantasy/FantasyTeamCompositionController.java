package com.dg.f1fantasyback.controller.fantasy;

import com.dg.f1fantasyback.model.dto.fantasy_team_composition.FantasyTeamCompositionCreateDto;
import com.dg.f1fantasyback.model.dto.fantasy_team_composition.FantasyTeamCompositionDto;
import com.dg.f1fantasyback.model.entity.fantasy.FantasyTeamComposition;
import com.dg.f1fantasyback.service.FantasyTeamCompositionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fantasy-teams/")
public class FantasyTeamCompositionController {
    private final FantasyTeamCompositionService teamCompositionService;

    public FantasyTeamCompositionController(FantasyTeamCompositionService teamCompositionService) {
        this.teamCompositionService = teamCompositionService;
    }

    @PutMapping("/update-composition")
    public FantasyTeamCompositionDto updateComposition(@RequestBody FantasyTeamCompositionCreateDto createDto) {
        return teamCompositionService.updateComposition(createDto);
    }
}
