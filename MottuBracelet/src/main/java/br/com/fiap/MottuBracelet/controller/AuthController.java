package br.com.fiap.MottuBracelet.controller;

import br.com.fiap.MottuBracelet.dto.request.AuthRequest;
import br.com.fiap.MottuBracelet.dto.request.RegisterRequest;
import br.com.fiap.MottuBracelet.dto.request.ChangePasswordRequest;
import br.com.fiap.MottuBracelet.dto.request.DeleteAccountRequest;
import br.com.fiap.MottuBracelet.dto.response.AuthResponse;
import br.com.fiap.MottuBracelet.model.Usuario;
import br.com.fiap.MottuBracelet.repository.UsuarioRepository;
import br.com.fiap.MottuBracelet.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;
    private final UsuarioRepository usuarioRepository;

    public AuthController(AuthService authService,
                          UsuarioRepository usuarioRepository) {
        this.authService = authService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<AuthResponse> getUserByEmail(@PathVariable String email) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Usuario u = usuarioOpt.get();
        AuthResponse response = new AuthResponse(
                null,
                u.getId(),
                u.getNome(),
                u.getEmail()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        authService.changePassword(request);
        return ResponseEntity.ok().build();
    }

    // 👇 NOVO: deletar conta
    @PostMapping("/delete-account")
    public ResponseEntity<?> deleteAccount(@RequestBody DeleteAccountRequest request) {
        authService.deleteAccount(request);
        return ResponseEntity.ok().build();
    }
}
