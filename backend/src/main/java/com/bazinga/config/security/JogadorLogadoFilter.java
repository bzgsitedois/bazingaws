package com.bazinga.config.security;


import com.bazinga.repository.JogadorRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JogadorLogadoFilter extends OncePerRequestFilter {

    private final JogadorRepository jogadorRepository;
    private final TokenService tokenService;


    public JogadorLogadoFilter(JogadorRepository jogadorRepository, TokenService tokenService) {
        this.jogadorRepository = jogadorRepository;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(request, response);
    }



}
