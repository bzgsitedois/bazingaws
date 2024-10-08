package com.bazinga.config.security;


import com.bazinga.entity.Jogador;
import com.bazinga.repository.JogadorRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final JogadorRepository jogadorRepository;

    public UserDetailsServiceImpl(JogadorRepository usuarioRepository) {
        this.jogadorRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Jogador jogador = jogadorRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return jogador;
    }
}
