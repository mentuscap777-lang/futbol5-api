package com.futbol_5.api.DTO;


// ==========================================
// IMPORTS VALIDACIÓN
// ==========================================
import jakarta.validation.constraints.NotBlank;

// ==========================================
// IMPORTS LOMBOK
// ==========================================
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequestDTO {

    @NotBlank(message = "El username es obligatorio")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}