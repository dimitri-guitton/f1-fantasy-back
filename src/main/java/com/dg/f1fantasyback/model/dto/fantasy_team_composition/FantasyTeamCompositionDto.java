package com.dg.f1fantasyback.model.dto.fantasy_team_composition;

import com.dg.f1fantasyback.model.entity.fantasy.FantasyTeamComposition;
import com.dg.f1fantasyback.model.dto.constructor.ConstructorDetailDto;
import com.dg.f1fantasyback.model.dto.driver.DriverDetailDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * DTO for {@link FantasyTeamComposition}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FantasyTeamCompositionDto implements Serializable {
    @NotNull
    private Set<ConstructorDetailDto> constructors = new LinkedHashSet<>();
    @NotNull
    private Set<DriverDetailDto> drivers = new LinkedHashSet<>();
}