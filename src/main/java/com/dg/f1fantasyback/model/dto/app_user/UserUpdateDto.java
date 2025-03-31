package com.dg.f1fantasyback.model.dto.app_user;

import com.dg.f1fantasyback.model.entity.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link AppUser}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto implements Serializable {
    private String username;
    private Boolean enabled;
    private String password;
}