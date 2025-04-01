package com.dg.f1fantasyback.model.dto.fantasy_team_composition;

import com.dg.f1fantasyback.model.entity.fantasy.FantasyTeamComposition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link FantasyTeamComposition}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FantasyTeamCompositionCreateDto implements Serializable {
    private Integer grandPrixId;
    private Long fantasyTeamId;
    private Set<Integer> constructorIds;
    private Set<Integer> driverIds;
}