package com.futbol_5.api.service.impl;

// ==========================================
// IMPORTS SPRING
// ==========================================
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// ==========================================
// IMPORTS DTOs
// ==========================================
import com.futbol_5.api.DTO.PlayerScoreDTO;
import com.futbol_5.api.DTO.StarterResponseDTO;
import com.futbol_5.api.DTO.TrainingRequestDTO;

// ==========================================
// IMPORTS ENTIDADES
// ==========================================
import com.futbol_5.api.entity.Player;
import com.futbol_5.api.entity.PlayerScore;
import com.futbol_5.api.entity.Training;

// ==========================================
// IMPORTS EXCEPCIONES
// ==========================================
import com.futbol_5.api.exception.InsufficientTrainingsException;
import com.futbol_5.api.exception.ResourceNotFoundException;

// ==========================================
// IMPORTS REPOSITORIOS
// ==========================================
import com.futbol_5.api.repository.PlayerRepository;
import com.futbol_5.api.repository.TrainingRepository;

// ==========================================
// IMPORTS SERVICIO
// ==========================================
import com.futbol_5.api.service.TrainingService;

// ==========================================
// IMPORTS JAVA
// ==========================================
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Transactional
public class TrainingServiceImpl implements TrainingService {

    // ==========================================
    // CONSTANTES DE NEGOCIO
    // ==========================================
    private static final int REQUIRED_TRAININGS       = 3;
    private static final BigDecimal SHOT_POWER_WEIGHT = new BigDecimal("0.20");
    private static final BigDecimal SPEED_WEIGHT      = new BigDecimal("0.30");
    private static final BigDecimal PASSES_WEIGHT     = new BigDecimal("0.50");
    private static final int STARTERS_COUNT           = 5; // Cambia a 11 para fútbol 11

    private final TrainingRepository trainingRepository;
    private final PlayerRepository playerRepository;

    public TrainingServiceImpl(
            TrainingRepository trainingRepository,
            PlayerRepository playerRepository) {
        this.trainingRepository = trainingRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public void saveTraining(TrainingRequestDTO request) {
        Training training = new Training();
        training.setWeekNumber(request.getWeekNumber());
        training.setTrainingNumber(request.getTrainingNumber());

        List<PlayerScore> scores = request.getPlayers().stream()
                .map(dto -> buildPlayerScore(dto, training))
                .collect(Collectors.toList());

        training.setPlayerScores(scores);
        trainingRepository.save(training);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StarterResponseDTO> getStarters(Integer weekNumber) {
        long count = trainingRepository.countByWeekNumber(weekNumber);

        if (count < REQUIRED_TRAININGS) {
            throw new InsufficientTrainingsException(
                    String.format(
                            "No hay suficiente información. Se realizaron %d de %d entrenamientos requeridos para la semana %d.",
                            count, REQUIRED_TRAININGS, weekNumber
                    )
            );
        }

        List<Training> trainings = trainingRepository
                .findByWeekNumberWithPlayers(weekNumber);

        // Agrupa puntajes por jugador
        Map<String, List<BigDecimal>> scoresByPlayer = new HashMap<>();
        trainings.forEach(training ->
                training.getPlayerScores().forEach(ps ->
                        scoresByPlayer
                                .computeIfAbsent(ps.getPlayer().getName(), k -> new ArrayList<>())
                                .add(ps.getScore())
                )
        );

        // Calcula promedio, ordena y asigna posición
        AtomicInteger position = new AtomicInteger(1);

        return scoresByPlayer.entrySet().stream()
                .map(entry -> {
                    BigDecimal sum = entry.getValue().stream()
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal average = sum.divide(
                            BigDecimal.valueOf(entry.getValue().size()),
                            2, RoundingMode.HALF_UP
                    );
                    return new StarterResponseDTO(entry.getKey(), average, null);
                })
                .sorted(Comparator.comparing(StarterResponseDTO::getAverageScore).reversed())
                .limit(STARTERS_COUNT)
                .peek(s -> s.setPosition("Titular #" + position.getAndIncrement()))
                .collect(Collectors.toList());
    }

    // Calcula el puntaje y construye la entidad PlayerScore
    private PlayerScore buildPlayerScore(PlayerScoreDTO dto, Training training) {
        Player player = playerRepository.findById(dto.getPlayerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Jugador no encontrado con ID: " + dto.getPlayerId()
                ));

        BigDecimal score = dto.getShotPower().multiply(SHOT_POWER_WEIGHT)
                .add(dto.getSpeed().multiply(SPEED_WEIGHT))
                .add(BigDecimal.valueOf(dto.getEffectivePasses()).multiply(PASSES_WEIGHT))
                .setScale(2, RoundingMode.HALF_UP);

        PlayerScore ps = new PlayerScore();
        ps.setTraining(training);
        ps.setPlayer(player);
        ps.setShotPower(dto.getShotPower());
        ps.setSpeed(dto.getSpeed());
        ps.setEffectivePasses(dto.getEffectivePasses());
        ps.setScore(score);
        return ps;
    }
}