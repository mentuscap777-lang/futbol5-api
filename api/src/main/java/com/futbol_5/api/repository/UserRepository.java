package com.futbol_5.api.repository;

// ==========================================
// IMPORTS SPRING DATA JPA
// ==========================================
import org.springframework.data.jpa.repository.JpaRepository;

// ==========================================
// IMPORTS JAVA
// ==========================================
import java.util.Optional;

// ==========================================
// IMPORTS ENTIDAD
// ==========================================
import com.futbol_5.api.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    // Spring genera automáticamente:
    // SELECT * FROM users WHERE username = ?
    Optional<User> findByUsername(String username);
}