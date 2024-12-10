package com.inventario.backend_inventario.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventario.backend_inventario.entity.dto.LoginDto;
import com.inventario.backend_inventario.service.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;


    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        String token = authenticationService.authenticateAndGenerateToken(loginDto.getEmail(), loginDto.getSenha());
        return ResponseEntity.ok(token);
    }
}
