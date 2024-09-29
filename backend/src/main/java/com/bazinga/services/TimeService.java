package com.bazinga.services;


import com.bazinga.dto.TimeProjectionDTO;
import com.bazinga.entity.Time;
import com.bazinga.mapper.TimeMapper;
import com.bazinga.repository.TimeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TimeService {
    private final TimeRepository timeRepository;
    private final TimeMapper timeMapper;

    public TimeService(TimeRepository timeRepository, TimeMapper timeMapper) {

        this.timeRepository = timeRepository;
        this.timeMapper = timeMapper;
    }

    public Optional<TimeProjectionDTO> findById(long id) {
        Optional<Time> time = timeRepository.findTimeWithJogadoresAndCategoriasById(id);
        return time.map(timeMapper::toTimeProjectionDTO);
    }

    public void deleteEntity(Long id) {
        timeRepository.deleteById(id);
    }

}