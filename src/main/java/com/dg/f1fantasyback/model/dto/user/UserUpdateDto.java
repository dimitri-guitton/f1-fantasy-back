package com.dg.f1fantasyback.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.dg.f1fantasyback.model.entity.User}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto implements Serializable {
    private String username;
    private Boolean enabled;
}