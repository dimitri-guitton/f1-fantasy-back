package com.dg.f1fantasyback.model.dto.price;

import com.dg.f1fantasyback.model.dto.driver.DriverDto;
import com.dg.f1fantasyback.model.dto.team.TeamDto;
import com.dg.f1fantasyback.model.entity.Price;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link Price}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceDto implements Serializable {
    private DriverDto driver;
    private TeamDto team;
    private Long price;
}