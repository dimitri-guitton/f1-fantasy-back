package com.dg.f1fantasyback.model.dto.fantasy_team;

import com.dg.f1fantasyback.model.dto.app_user.UserDetailDto;
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
public class FantasyTeamDto implements Serializable {
    private UserDetailDto user;
    private String label;
}