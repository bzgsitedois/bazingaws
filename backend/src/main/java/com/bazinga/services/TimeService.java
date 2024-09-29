package com.bazinga.services;


import com.bazinga.dto.TimeProjectionDTO;
import com.bazinga.entity.Time;
import com.bazinga.mapper.TimeMapper;
import com.bazinga.repository.TimeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}