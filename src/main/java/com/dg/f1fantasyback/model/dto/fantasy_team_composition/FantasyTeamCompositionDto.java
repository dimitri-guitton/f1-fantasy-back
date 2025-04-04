package com.dg.f1fantasyback.model.dto.fantasy_team_composition;

import com.dg.f1fantasyback.model.dto.constructor.ConstructorDto;
import com.dg.f1fantasyback.model.dto.driver.DriverDto;
import com.dg.f1fantasyback.model.entity.fantasy.FantasyTeamComposition;
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
    private Set<ConstructorDto> constructors = new LinkedHashSet<>();
    @NotNull
    private Set<DriverDto> drivers = new LinkedHashSet<>();
}