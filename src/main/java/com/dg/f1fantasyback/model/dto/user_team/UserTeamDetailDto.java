package com.dg.f1fantasyback.model.dto.user_team;

import com.dg.f1fantasyback.model.dto.driver.DriverDto;
import com.dg.f1fantasyback.model.dto.user.UserDetailDto;
import com.dg.f1fantasyback.model.entity.UserTeam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * DTO for {@link UserTeam}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTeamDetailDto implements Serializable {
    private Long id;
    private UserDetailDto user;
    private String label;
    private Set<DriverDto> drivers = new LinkedHashSet<>();
}