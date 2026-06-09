package com.futbol_5.api.service;

// ==========================================
// IMPORTS DTOs
// ==========================================
import com.futbol_5.api.DTO.AuthResponseDTO;
import com.futbol_5.api.DTO.LoginRequestDTO;
import com.futbol_5.api.DTO.RegisterRequestDTO;

public interface AuthService {

    AuthResponseDTO register(RegisterRequestDTO request);

    AuthResponseDTO login(LoginRequestDTO request);
}