package com.dg.f1fantasyback.model.dto.fantasy_team;

import com.dg.f1fantasyback.model.dto.driver.DriverDto;
import com.dg.f1fantasyback.model.dto.constructor.ConstructorDto;
import com.dg.f1fantasyback.model.dto.app_user.UserDetailDto;
import com.dg.f1fantasyback.model.entity.fantasy.FantasyTeam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * DTO for {@link FantasyTeam}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FantasyTeamDetailDto implements Serializable {
    private Long id;
    private UserDetailDto user;
    private String label;
    private Set<DriverDto> drivers = new LinkedHashSet<>();
    private Set<ConstructorDto> teams = new LinkedHashSet<>();
}