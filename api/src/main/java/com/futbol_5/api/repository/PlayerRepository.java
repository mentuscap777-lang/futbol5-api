package com.futbol_5.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.futbol_5.api.entity.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}