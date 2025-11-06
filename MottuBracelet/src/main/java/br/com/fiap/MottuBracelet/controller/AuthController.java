package br.com.fiap.MottuBracelet.controller;

import br.com.fiap.MottuBracelet.dto.request.AuthRequest;
import br.com.fiap.MottuBracelet.dto.response.AuthResponse;
import br.com.fiap.MottuBracelet.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*") // libera pro app RN
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}