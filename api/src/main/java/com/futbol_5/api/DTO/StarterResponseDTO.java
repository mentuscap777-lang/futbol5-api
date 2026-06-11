package com.futbol_5.api.DTO;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StarterResponseDTO {

    private String playerName;
    private BigDecimal averageScore;
    private String position;
}