package com.bazinga.controllers;

import com.bazinga.dto.TimeProjectionDTO;
import com.bazinga.entity.CategoriaEntity;
import com.bazinga.entity.Jogador;
import com.bazinga.entity.Time;
import com.bazinga.repository.TimeRepository;
import com.bazinga.services.TimeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/time")
public class TimeController {

    private final TimeService timeService;
    private final TimeRepository timeRepository;


    public TimeController(TimeService timeService, TimeRepository timeRepository) {
        this.timeService = timeService;
        this.timeRepository = timeRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeProjectionDTO> buscarPeloId(@PathVariable Long id) {
        TimeProjectionDTO timeDTO = timeService.findById(id)
                .orElseThrow(() -> new RuntimeException("Time não encontrado com o ID: " + id));
        return ResponseEntity.ok(timeDTO);
    }
}
