package com.dg.f1fantasyback.model.dto.user_team;

import com.dg.f1fantasyback.model.dto.user.UserDetailDto;
import com.dg.f1fantasyback.model.entity.UserTeam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link UserTeam}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTeamDto implements Serializable {
    private UserDetailDto user;
    private String label;
}