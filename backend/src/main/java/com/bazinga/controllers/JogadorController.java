package com.bazinga.controllers;


import com.bazinga.bases.BasePagination;
import com.bazinga.dto.JogadorDTOs.*;
import com.bazinga.dto.TimeDTOs.TimeFilter;
import com.bazinga.dto.TimeDTOs.TimeListAllDTO;
import com.bazinga.dto.TimeDTOs.TimeProjectionDTO;
import com.bazinga.entity.Jogador;
import com.bazinga.entity.Time;
import com.bazinga.repository.JogadorRepository;
import com.bazinga.services.JogadorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    private ResponseEntity<?> newEntity(@RequestBody @Valid JogadorCreateDTO dto) {
        jogadorService.newEntity(dto);
        return new ResponseEntity<>("Jogador Criado", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> updateEntity(@PathVariable Long id, @RequestBody @Valid JogadorUpdateDTO dto) {
        Jogador jogadorAtualizado = jogadorService.updateEntity(dto,id);
        if (jogadorAtualizado != null) {
            return new ResponseEntity<>("Jogador Atualizado", HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    private ResponseEntity<String> delete(@PathVariable Long id) {
        Optional<JogadorProjectionDTO> entity = jogadorService.findById(id);
        entity.ifPresentOrElse(
                e -> jogadorService.deleteEntity(id),
                () -> {
                    throw new RuntimeException("Não foi possível encontrar o parâmetro de id: " + id);
                }
        );
        return new ResponseEntity<>("Objeto deletado", HttpStatus.OK);
    }

    @PostMapping(value = "/listAll" , params = {"page", "size"})
    private ResponseEntity<BasePagination<JogadorListAllDTO>> listAll(@RequestParam(defaultValue = "0", required = false) Integer page,
  @RequestParam(defaultValue = "10", required = false) Integer size,
  @RequestBody JogadorFilter jogadorFilter,
  HttpServletRequest request){
        if (size <= 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        return jogadorService.listAll(page, size, jogadorFilter, request);
    }

}
