package com.dg.f1fantasyback.model.dto.constructor;

import com.dg.f1fantasyback.model.entity.racing.Constructor;
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
public class ConstructorDto implements Serializable {
    private String label;
    private String logo;
}