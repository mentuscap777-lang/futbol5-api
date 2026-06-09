package com.futbol_5.api.DTO;

// ==========================================
// IMPORTS VALIDACIÓN
// ==========================================
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

// ==========================================
// IMPORTS JAVA
// ==========================================
import java.math.BigDecimal;

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
public class PlayerScoreDTO {

    @NotNull(message = "El ID del jugador es obligatorio")
    private Long playerId;

    @NotNull(message = "La potencia de tiro es obligatoria")
    @DecimalMin(value = "0.0", message = "La potencia de tiro no puede ser negativa")
    private BigDecimal shotPower;

    @NotNull(message = "La velocidad es obligatoria")
    @DecimalMin(value = "0.0", message = "La velocidad no puede ser negativa")
    private BigDecimal speed;

    @NotNull(message = "Los pases efectivos son obligatorios")
    @Min(value = 0, message = "Los pases efectivos no pueden ser negativos")
    private Integer effectivePasses;
}