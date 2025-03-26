package com.dg.f1fantasyback.model.dto.team;

import com.dg.f1fantasyback.model.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link Team}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDto implements Serializable {
    private Integer id;
    private String label;
    private String logo;
}