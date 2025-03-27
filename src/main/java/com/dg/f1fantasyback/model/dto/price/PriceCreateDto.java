package com.dg.f1fantasyback.model.dto.price;

import com.dg.f1fantasyback.model.entity.Price;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
public class PriceCreateDto implements Serializable {
    private Integer driverId;
    private Integer teamId;

    @NotNull
    @PositiveOrZero
    private Long price;
}