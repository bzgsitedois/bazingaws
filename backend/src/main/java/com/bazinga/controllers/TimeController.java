package com.bazinga.controllers;

import com.bazinga.bases.BasePagination;
import com.bazinga.dto.*;
import com.bazinga.entity.CategoriaEntity;
import com.bazinga.entity.Jogador;
import com.bazinga.entity.Time;
import com.bazinga.repository.TimeRepository;
import com.bazinga.services.TimeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/time")
public class TimeController {

    private final TimeService timeService;
    private final TimeRepository timeRepository;


    private TimeController(TimeService timeService, TimeRepository timeRepository) {
        this.timeService = timeService;
        this.timeRepository = timeRepository;
    }

    @GetMapping("/{id}")
    private ResponseEntity<TimeProjectionDTO> buscarPeloId(@PathVariable Long id) {
        TimeProjectionDTO timeDTO = timeService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Time não encontrado com o ID: " + id));
        return ResponseEntity.ok(timeDTO);
    }

    @DeleteMapping(value = "/{id}")
    private ResponseEntity<String> delete(@PathVariable Long id) {
        Optional<TimeProjectionDTO> entity = timeService.findById(id);
        entity.ifPresentOrElse(
                e -> timeService.deleteEntity(id),
                () -> {
                    throw new RuntimeException("Não foi possível encontrar o parâmetro de id: " + id);
                }
        );
        return new ResponseEntity<>("Objeto deletado", HttpStatus.OK);
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
}
