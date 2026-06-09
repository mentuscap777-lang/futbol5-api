package com.futbol_5.api.service.impl;

// ==========================================
// IMPORTS SPRING
// ==========================================
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// ==========================================
// IMPORTS DTOs
// ==========================================
import com.futbol_5.api.DTO.AuthResponseDTO;
import com.futbol_5.api.DTO.LoginRequestDTO;
import com.futbol_5.api.DTO.RegisterRequestDTO;

// ==========================================
// IMPORTS ENTIDAD
// ==========================================
import com.futbol_5.api.entity.User;

// ==========================================
// IMPORTS REPOSITORIO
// ==========================================
import com.futbol_5.api.repository.UserRepository;

// ==========================================
// IMPORTS SECURITY
// ==========================================
import com.futbol_5.api.security.JwtService;

// ==========================================
// IMPORTS SERVICIO
// ==========================================
import com.futbol_5.api.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResponseDTO register(RegisterRequestDTO request) {
        // Verifica que el username no esté en uso
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("El username ya está en uso");
        }

        // Crea el usuario con la contraseña encriptada
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        userRepository.save(user);

        // Genera y retorna el token JWT
        String token = jwtService.generateToken(user);
        return new AuthResponseDTO(token);
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {
        // Spring Security verifica username y password automáticamente
        // Lanza excepción si las credenciales son incorrectas
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // Si llegó aquí, las credenciales son correctas
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = jwtService.generateToken(user);
        return new AuthResponseDTO(token);
    }
}