package com.dg.f1fantasyback.model.dto.fantasy_team;

import com.dg.f1fantasyback.model.dto.app_user.UserDetailDto;
import com.dg.f1fantasyback.model.entity.fantasy.FantasyTeam;
import jakarta.validation.constraints.NotNull;
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
public class FantasyTeamDto implements Serializable {
    @NotNull
    private Long id;
    @NotNull
    private UserDetailDto user;
    @NotNull
    private String label;
}