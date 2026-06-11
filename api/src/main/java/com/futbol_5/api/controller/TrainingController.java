package com.futbol_5.api.controller;

/**
 * Imports Swagger
 */
import org.springframework.web.bind.annotation.CrossOrigin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Imports Validation
 */
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

// ==========================================
// IMPORTS SPRING
// ==========================================
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// ==========================================
// IMPORTS DTOs
// ==========================================
import com.futbol_5.api.DTO.StarterResponseDTO;
import com.futbol_5.api.DTO.TrainingRequestDTO;

// ==========================================
// IMPORTS SERVICIO
// ==========================================
import com.futbol_5.api.service.TrainingService;

// ==========================================
// IMPORTS JAVA
// ==========================================
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/trainings")
@Validated
@Tag(name = "Entrenamientos", description = "Endpoints para gestionar entrenamientos y titulares")
@SecurityRequirement(name = "bearerAuth") // Indica en Swagger que requiere token
public class TrainingController {

    private final TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    // POST /api/v1/trainings — solo ADMIN
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Registrar entrenamiento",
            description = "Registra los resultados de un entrenamiento. Solo ADMIN."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Entrenamiento registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "No autorizado — requiere rol ADMIN")
    })
    public ResponseEntity<Map<String, Object>> saveTraining(
            @Valid @RequestBody TrainingRequestDTO request) {
        trainingService.saveTraining(request);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Entrenamiento registrado exitosamente");
        response.put("weekNumber", request.getWeekNumber());
        response.put("trainingNumber", request.getTrainingNumber());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /api/v1/trainings/starters?weekNumber=1 — ADMIN y USER
    @GetMapping("/starters")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(
            summary = "Obtener equipo titular",
            description = "Retorna los 5 titulares si se completaron los 3 entrenamientos de la semana."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Titulares retornados exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "422", description = "Menos de 3 entrenamientos registrados")
    })
    public ResponseEntity<List<StarterResponseDTO>> getStarters(
            @RequestParam
            @NotNull(message = "El número de semana es obligatorio")
            @Min(value = 1, message = "La semana debe ser mayor a 0")
            Integer weekNumber) {
        List<StarterResponseDTO> starters = trainingService.getStarters(weekNumber);
        return ResponseEntity.ok(starters);
    }
}