package com.futbol_5.api.service;

import com.futbol_5.api.DTO.StarterResponseDTO;
import com.futbol_5.api.DTO.TrainingRequestDTO;
import java.util.List;

public interface TrainingService {

    void saveTraining(TrainingRequestDTO request);

    List<StarterResponseDTO> getStarters(Integer weekNumber);
}