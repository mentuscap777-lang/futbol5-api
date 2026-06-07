package com.futbol_5.api.repository;

// ==========================================
// IMPORTS SPRING DATA JPA
// ==========================================
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// ==========================================
// IMPORTS JAVA
// ==========================================
import java.util.List;

// ==========================================
// IMPORTS ENTIDAD
// ==========================================
import com.futbol_5.api.entity.Training;

public interface TrainingRepository extends JpaRepository<Training, Long> {

    // Cuenta cuántos entrenamientos hay en una semana
    // SELECT COUNT(*) FROM training WHERE week_number = ?
    long countByWeekNumber(Integer weekNumber);

    // Trae los entrenamientos con sus jugadores en una sola query
    // JOIN FETCH evita el problema N+1
    @Query("SELECT t FROM Training t JOIN FETCH t.playerScores ps JOIN FETCH ps.player WHERE t.weekNumber = :weekNumber")
    List<Training> findByWeekNumberWithPlayers(@Param("weekNumber") Integer weekNumber);
}