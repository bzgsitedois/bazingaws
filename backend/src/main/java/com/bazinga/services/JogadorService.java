package com.bazinga.services;

import com.bazinga.dto.JogadorSemTimeDTO;
import com.bazinga.entity.Jogador;
import com.bazinga.entity.Time;
import com.bazinga.repository.JogadorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JogadorService {

    private final JogadorRepository jogadorRepository;

    public JogadorService(JogadorRepository jogadorRepository) {
        this.jogadorRepository = jogadorRepository;
    }

    public void atualizarJogadoresDoTime(Time time, List<Long> novosUsuariosIds) {
        List<Jogador> usuariosAtuais = jogadorRepository.findByTimeId(time.getId());

        List<Long> idsUsuariosAtuais = usuariosAtuais.stream()
                .map(Jogador::getId)
                .toList();

        List<Long> idsParaRemover = idsUsuariosAtuais.stream()
                .filter(id -> !novosUsuariosIds.contains(id))
                .toList();

        if (!idsParaRemover.isEmpty()) {
            jogadorRepository.removerUsuariosDoTime(idsParaRemover);
        }

        List<Long> idsParaAdicionar = novosUsuariosIds.stream()
                .filter(id -> !idsUsuariosAtuais.contains(id))
                .toList();

        if (!idsParaAdicionar.isEmpty()) {
            jogadorRepository.adicionarUsuariosAoTime(time.getId(), idsParaAdicionar);
        }
    }

    public List<JogadorSemTimeDTO> buscarJogadoresSemTime() {
        List<Object[]> resultados = jogadorRepository.findJogadoresSemTime();
        List<JogadorSemTimeDTO> jogadores = new ArrayList<>();

        for (Object[] resultado : resultados) {
            Long id = (Long) resultado[0];
            String nome = (String) resultado[1];
            jogadores.add(new JogadorSemTimeDTO(id, nome));
        }
        return jogadores;
    }

}
