package com.futbol_5.api.DTO;
// ==========================================
// IMPORTS LOMBOK
// ==========================================
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {

    // El token JWT que el cliente debe guardar
    // y enviar en cada request como: Authorization: Bearer <token>
    private String token;
}