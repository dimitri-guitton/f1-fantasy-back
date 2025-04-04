package com.dg.f1fantasyback.model.dto.market_value;

import com.dg.f1fantasyback.model.dto.constructor.ConstructorDto;
import com.dg.f1fantasyback.model.dto.driver.DriverDto;
import com.dg.f1fantasyback.model.entity.fantasy.MarketValue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link MarketValue}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketValueDto implements Serializable {
    private DriverDto driver;
    private ConstructorDto constructor;
    @NotNull
    private Long value;
}