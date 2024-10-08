package com.bazinga.services;


import com.bazinga.config.security.TokenService;
import com.bazinga.dto.LoginDTO;
import com.bazinga.repository.JogadorRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

private final TokenService tokenService;
private final AuthenticationManager authenticationManager;
private final UserDetailsService userDetailsService;
private final JogadorRepository repository;


    public AuthService(TokenService tokenService, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JogadorRepository repository) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.repository = repository;
    }

    public String login(LoginDTO loginDto) {
        try {
            var senha = new UsernamePasswordAuthenticationToken(loginDto.login(), loginDto.senha());

            Authentication auth = authenticationManager.authenticate(senha);

            UserDetails userDetails = (UserDetails) auth.getPrincipal();

            return tokenService.generateToken(userDetails);
        } catch (AuthenticationException e) {
            throw new RuntimeException();
        }
    }
}
