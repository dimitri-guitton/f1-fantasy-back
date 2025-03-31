package com.dg.f1fantasyback.model.dto.fantasy_team;

import com.dg.f1fantasyback.model.entity.fantasy.FantasyTeam;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class FantasyTeamCreateDto implements Serializable {
    @NotNull(message = "Le label de l'équipe est obligatoire")
    @NotBlank(message = "Le label de l'équipe est obligatoire")
    @Size(min = 3, max = 50, message = "Le label de l'équipe doit contenir entre 3 et 50 caractères")
    private String label;

    private Set<Integer> driverIds = new LinkedHashSet<>();

    private Set<Integer> teamIds = new LinkedHashSet<>();
}