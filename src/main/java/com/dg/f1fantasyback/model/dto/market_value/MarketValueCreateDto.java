package com.dg.f1fantasyback.model.dto.market_value;

import com.dg.f1fantasyback.model.entity.fantasy.MarketValue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
public class MarketValueCreateDto implements Serializable {
    private Integer driverId;
    private Integer teamId;

    @NotNull
    @PositiveOrZero
    private Long value;
}