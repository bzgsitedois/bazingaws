package com.bazinga.controllers;


import com.bazinga.dto.JogadorProjectionDTO;
import com.bazinga.dto.JogadorSemTimeDTO;
import com.bazinga.dto.TimeDTOs.TimeProjectionDTO;
import com.bazinga.entity.Jogador;
import com.bazinga.repository.JogadorRepository;
import com.bazinga.services.JogadorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jogador")
public class JogadorController {

    private final JogadorRepository jogadorRepository;
    private final JogadorService jogadorService;

    public JogadorController(JogadorRepository jogadorRepository, JogadorService jogadorService) {
        this.jogadorRepository = jogadorRepository;
        this.jogadorService = jogadorService;
    }

    @GetMapping("/semTime")
    private ResponseEntity<?> semTime() {
        List<JogadorSemTimeDTO> jogadores = jogadorService.buscarJogadoresSemTime();
        if (jogadores.isEmpty()) {
            return ResponseEntity.ok("Todos os jogadores já têm time");
        }

        return ResponseEntity.ok(jogadores);
    }

    @GetMapping("/{id}")
    private ResponseEntity<JogadorProjectionDTO> findById(@PathVariable Long id) {
        JogadorProjectionDTO jogadorDto = jogadorService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Jogador não encontrado com o ID: " + id));
        return ResponseEntity.ok(jogadorDto);
    }


}
