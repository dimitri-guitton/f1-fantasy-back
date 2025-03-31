package com.dg.f1fantasyback.model.dto.driver;

import com.dg.f1fantasyback.model.entity.racing.Driver;
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
    private String firstName;
    private String lastName;
    private String profilePicture;
}