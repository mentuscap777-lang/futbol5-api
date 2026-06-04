package com.futbol_5.api.entity;

/**
 * IMPORTS JPA
 */

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * IMPORTS JAVA
 */
import java.math.BigDecimal;


/**
 * IMPORTS LOMBOK
 */
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "player_score")
@Getter
@Setter
@NoArgsConstructor
public class PlayerScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;

    @Column(name = "shot_power", nullable = false, precision = 5, scale = 2)
    private BigDecimal shotPower;

    @Column(name = "speed", nullable = false, precision = 5, scale = 2)
    private BigDecimal speed;

    @Column(name = "effective_passes", nullable = false)
    private Integer effectivePasses;

    @Column(name = "score", nullable = false, precision = 5, scale = 2)
    private BigDecimal score;
}