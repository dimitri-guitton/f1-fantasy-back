package com.dg.f1fantasyback.model.dto.driver;

import com.dg.f1fantasyback.model.entity.racing.Driver;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link Driver}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverDto implements Serializable {
    @NotNull
    private Integer id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String profilePicture;
}