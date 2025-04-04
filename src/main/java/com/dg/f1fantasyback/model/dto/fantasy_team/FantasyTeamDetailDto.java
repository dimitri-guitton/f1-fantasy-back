package com.dg.f1fantasyback.model.dto.fantasy_team;

import com.dg.f1fantasyback.model.dto.fantasy_team_composition.FantasyTeamCompositionDto;
import com.dg.f1fantasyback.model.entity.fantasy.FantasyTeam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link FantasyTeam}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FantasyTeamDetailDto implements Serializable {
    private Long id;
    private String label;
    private FantasyTeamCompositionDto composition;
}