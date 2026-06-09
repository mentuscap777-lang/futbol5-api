package com.futbol_5.api.DTO;

// ==========================================
// IMPORTS VALIDACIÓN
// ==========================================
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// ==========================================
// IMPORTS ENTIDAD
// ==========================================
import com.futbol_5.api.entity.User;

// ==========================================
// IMPORTS LOMBOK
// ==========================================
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequestDTO {

    @NotBlank(message = "El username es obligatorio")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    @NotNull(message = "El rol es obligatorio")
    private User.Role role;
}