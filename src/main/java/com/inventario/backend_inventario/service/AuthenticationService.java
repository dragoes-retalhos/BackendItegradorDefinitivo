package com.inventario.backend_inventario.service;

import org.springframework.stereotype.Service;

import com.inventario.backend_inventario.repository.UserRepository;
import com.inventario.backend_inventario.util.JwtUtil;
import com.inventario.backend_inventario.util.PasswordUtil;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordUtil passwordUtil;
    private final JwtUtil jwtUtil;


    public AuthenticationService(UserRepository userRepository, PasswordUtil passwordUtil, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordUtil = passwordUtil;
        this.jwtUtil = jwtUtil;
    }

    public String authenticateAndGenerateToken(String email, String rawPassword) {
        return userRepository.findByEmail(email)
                .filter(user -> passwordUtil.matches(rawPassword, user.getPassword()))
                .map(user -> jwtUtil.generateToken(user.getEmail()))
                .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));
    }
}

