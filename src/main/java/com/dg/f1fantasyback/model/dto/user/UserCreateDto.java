package com.dg.f1fantasyback.model.dto.user;

import com.dg.f1fantasyback.model.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto implements Serializable {
    @NotNull(message = "Le nom d'utilisateur est obligatoire")
    @NotBlank(message = "Le nom d'utilisateur ne peut pas être vide")
    @Size(min = 3, message = "Le nom d'utilisateurdoit contenir au moins 3 caractères")
    private String username;

    @NotNull(message = "Le mot de passe est obligatoire")
    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    @Size(message = "Le mot de passe doit contenir au moins 8 caractères", min = 8)
    private String password;
}