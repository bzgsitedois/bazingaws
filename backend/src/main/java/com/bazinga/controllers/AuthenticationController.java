package com.bazinga.controllers;

import com.bazinga.config.security.TokenService;
import com.bazinga.dto.LoginDTO;
import com.bazinga.repository.JogadorRepository;
import com.bazinga.services.AuthService;
import com.bazinga.services.JogadorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final JogadorRepository repository;

    private final AuthService authService;

    private final TokenService tokenService;

    private final JogadorService usuarioService;

//    private final EmailService emailService;

    public AuthenticationController( JogadorService usuarioService, JogadorRepository repository, AuthService authService, TokenService tokenService) {
        this.usuarioService = usuarioService;
        this.authService = authService;
        this.repository = repository;
        this.tokenService = tokenService;
    }

//    @Transactional
//    @PostMapping("/atualizarSenha")
//    public ResponseEntity<?> updateSenha(@Valid @RequestBody AtualizarSenhaDTO atualizarSenhaDTO) {
//        String emailUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
//
//        System.out.println(emailUsuario);
//
//        authService.updateSenha(emailUsuario, atualizarSenhaDTO.senha());
//        return ResponseEntity.ok().build();
//    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginDTO loginDto) {
        String token = authService.login(loginDto);
        return ResponseEntity.ok(token);
    }

//    @PostMapping("/validate")
//    public ResponseEntity<TokenInfo> validate(@RequestBody ValidateTokenDto tokenDto) {
//        return ResponseEntity.ok().body(tokenService.validateToken(tokenDto.token()));
//    }
//
//    @PostMapping(value = "/esqueceu-senha")
//    public ResponseEntity<UsuarioShallowDto> esqueceuSenha(@RequestBody RecuperacaoSenhaDTO recuperacaoSenhaDTO) throws Exception {
//        Usuario usuario = usuarioService.criarTokenParaSenha(recuperacaoSenhaDTO);
//        usuarioService.processarRecuperacaoSenha(usuario, usuario.getResetarSenhaToken());
//
//        return ResponseEntity.ok().body(new UsuarioShallowDto(usuario));
//    }
//
//    @PostMapping(value = "/alterar-senha")
//    public ResponseEntity<UsuarioShallowDto> alterarSenhaPerfil(@RequestBody RecuperacaoSenhaDTO recuperacaoSenhaDTO) throws Exception {
//        Usuario usuario = usuarioService.criarTokenParaSenha(recuperacaoSenhaDTO);
//        usuarioService.processarAlteracaoSenha(usuario, usuario.getResetarSenhaToken());
//
//        return ResponseEntity.ok().body(new UsuarioShallowDto(usuario));
//    }
//
//    @PostMapping(value = "/redefinir-senha")
//    public ResponseEntity<UsuarioPermissoesDTO> redefinirSenha(@RequestParam String token, @RequestParam String senha) {
//        usuarioService.redefinirSenhaPeloToken(token, senha);
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping(value = "/refreshToken")
//    public ResponseEntity<String> refreshToken() {
//        String newToken = authService.refreshToken();
//        return ResponseEntity.ok().body(newToken);
//    }
//
//    @PostMapping("/alterar-email")
//    public ResponseEntity<?> alterarEmail(@Valid @RequestBody AlterarEmailDTO alterarEmailDTO) throws Exception {
//
//        String emailUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
//        Usuario usuario = repository.findByEmail(emailUsuario)
//                .orElseThrow(UsuarioNaoEncontradoException::new);
//
//        authService.processarAlteracaoEmail(usuario.getId(), alterarEmailDTO.emailNovo());
//
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("/confirmar-email")
//    public ResponseEntity<?> confirmarEmail(@RequestParam("token") String token) {
//        authService.confirmarAlteracaoEmail(token);
//        return ResponseEntity.ok().build();
//    }
}

