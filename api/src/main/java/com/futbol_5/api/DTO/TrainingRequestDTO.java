package com.futbol_5.api.DTO;


// ==========================================
// IMPORTS VALIDACIÓN
// ==========================================
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

// ==========================================
// IMPORTS JAVA
// ==========================================
import java.util.List;

// ==========================================
// IMPORTS LOMBOK
// ==========================================
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TrainingRequestDTO {

    @NotNull(message = "El número de semana es obligatorio")
    @Min(value = 1, message = "La semana debe ser mayor a 0")
    private Integer weekNumber;

    @NotNull(message = "El número de entrenamiento es obligatorio")
    @Min(value = 1, message = "El número de entrenamiento debe ser 1, 2 o 3")
    @Max(value = 3, message = "El número de entrenamiento no puede ser mayor a 3")
    private Integer trainingNumber;

    @NotEmpty(message = "La lista de jugadores no puede estar vacía")
    @Valid
    private List<PlayerScoreDTO> players;
}