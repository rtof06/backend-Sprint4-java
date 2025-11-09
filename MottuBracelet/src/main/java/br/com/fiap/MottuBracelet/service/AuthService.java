package br.com.fiap.MottuBracelet.service;

import br.com.fiap.MottuBracelet.dto.request.AuthRequest;
import br.com.fiap.MottuBracelet.dto.request.RegisterRequest;
import br.com.fiap.MottuBracelet.dto.request.ChangePasswordRequest;
import br.com.fiap.MottuBracelet.dto.request.DeleteAccountRequest;
import br.com.fiap.MottuBracelet.dto.response.AuthResponse;
import br.com.fiap.MottuBracelet.model.Usuario;
import br.com.fiap.MottuBracelet.repository.UsuarioRepository;
import br.com.fiap.MottuBracelet.security.JwtService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepository,
                       JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
    }

    public AuthResponse login(AuthRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!request.getSenha().equals(usuario.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        String token = jwtService.generateToken(usuario.getEmail());

        return new AuthResponse(
                token,
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail()
        );
    }

    public AuthResponse register(RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("E-mail já cadastrado");
        }

        Usuario novo = new Usuario();
        novo.setNome(request.getNome());
        novo.setEmail(request.getEmail());
        novo.setSenha(request.getSenha());

        Usuario salvo = usuarioRepository.save(novo);

        String token = jwtService.generateToken(salvo.getEmail());

        return new AuthResponse(
                token,
                salvo.getId(),
                salvo.getNome(),
                salvo.getEmail()
        );
    }

    public void changePassword(ChangePasswordRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setSenha(request.getNovaSenha());
        usuarioRepository.save(usuario);
    }

    // 👇 NOVO: deletar conta
    public void deleteAccount(DeleteAccountRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // segurança simples: confirma a senha
        if (!request.getSenha().equals(usuario.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        usuarioRepository.delete(usuario);
    }
}
