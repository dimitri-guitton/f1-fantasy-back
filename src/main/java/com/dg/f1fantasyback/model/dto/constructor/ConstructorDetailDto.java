package com.dg.f1fantasyback.model.dto.constructor;

import com.dg.f1fantasyback.model.entity.racing.Constructor;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link Constructor}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConstructorDetailDto implements Serializable {
    @NotNull
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private String logo;
    @NotNull
    private Long marketValue;
}