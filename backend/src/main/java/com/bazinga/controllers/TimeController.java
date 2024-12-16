package com.bazinga.controllers;

import com.bazinga.bases.BasePagination;
import com.bazinga.dto.TimeDTOs.*;
import com.bazinga.entity.Time;
import com.bazinga.exception.TimeNaoEncontradoException;
import com.bazinga.repository.TimeRepository;
import com.bazinga.services.JogadorService;
import com.bazinga.services.TimeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/time")
public class TimeController {

    private final TimeService timeService;
    private final TimeRepository timeRepository;
    private final JogadorService jogadorService;


    private TimeController(TimeService timeService, TimeRepository timeRepository, JogadorService jogadorService) {
        this.timeService = timeService;
        this.timeRepository = timeRepository;
        this.jogadorService = jogadorService;
    }

    @GetMapping("/{id}")
    private ResponseEntity<TimeProjectionDTO> buscarPeloId(@PathVariable Long id) {
        TimeProjectionDTO timeDTO = timeService.findById(id)
                .orElseThrow(TimeNaoEncontradoException::new);
        return ResponseEntity.ok(timeDTO);
    }

    @DeleteMapping(value = "/{id}")
    private ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<TimeProjectionDTO> entity = timeService.findById(id);
        entity.ifPresentOrElse(
                e -> timeService.deleteEntity(id),
                () -> {
                    throw new RuntimeException("Não foi possível encontrar o parâmetro de id: " + id);
                }
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping
    private ResponseEntity<?> newEntity(@RequestBody @Valid TimeCreateDTO timeCreateDTO) {
        timeService.newEntity(timeCreateDTO);
        return new ResponseEntity<>("Time Criado", HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    private ResponseEntity<?> updateEntity(@PathVariable Long id, @Valid @RequestBody TimeUpdateDTO timeUpdateDTO) {
        Time timeAtualizado = timeService.updateEntity(id , timeUpdateDTO);
        if (timeAtualizado != null) {
        return new ResponseEntity<>("Time Atualizado", HttpStatus.OK);
    } else {
        return ResponseEntity.notFound().build();
    }
    }

    @PostMapping(value = "/listAll" , params = {"page", "size"})
    public ResponseEntity<BasePagination<TimeListAllDTO>> listAll(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer size,
            @RequestBody TimeFilter timeFilter,
            HttpServletRequest request) {

        if (size <= 0) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        return timeService.listAll(page, size, timeFilter, request);
    }

    @PostMapping("/{timeId}/jogadores/adicionar")
    public ResponseEntity<String> adicionarJogadoresAoTime(
            @PathVariable Long timeId,
            @RequestBody AddJogadoresTimeDTO jogadores) {
        jogadorService.adicionarJogadoresAoTime(timeId, jogadores.idsJogadores());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{timeId}/jogadores/remover")
    public ResponseEntity<String> removerJogadoresDoTime(
            @PathVariable Long timeId,
            @RequestBody AddJogadoresTimeDTO jogadores) {
        jogadorService.removerJogadoresDoTime(timeId, jogadores.idsJogadores());
        return ResponseEntity.ok().build();
    }


}
