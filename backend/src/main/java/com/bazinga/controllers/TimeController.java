package com.bazinga.controllers;

import com.bazinga.dto.TimeProjectionDTO;
import com.bazinga.entity.CategoriaEntity;
import com.bazinga.entity.Jogador;
import com.bazinga.entity.Time;
import com.bazinga.repository.TimeRepository;
import com.bazinga.services.TimeService;
import jakarta.persistence.EntityNotFoundException;
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
    public ResponseEntity<String> delete(@PathVariable Long id) {
        Optional<TimeProjectionDTO> entity = timeService.findById(id);
        entity.ifPresentOrElse(
                e -> timeService.deleteEntity(id),
                () -> {
                    throw new RuntimeException("Não foi possível encontrar o parâmetro de id: " + id);
                }
        );
        return new ResponseEntity<>("Objeto deletado", HttpStatus.OK);
    }
}
