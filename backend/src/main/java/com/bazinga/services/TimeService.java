package com.bazinga.services;


import com.bazinga.bases.BasePagination;
import com.bazinga.dto.TimeDTOs.*;
import com.bazinga.entity.JogoEntity;
import com.bazinga.entity.Jogador;
import com.bazinga.entity.Time;
import com.bazinga.mapper.TimeMapper;
import com.bazinga.repository.JogoRepository;
import com.bazinga.repository.JogadorRepository;
import com.bazinga.repository.TimeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TimeService {
    private final TimeRepository timeRepository;
    private final TimeMapper timeMapper;
    private final JogoRepository jogoRepository;
    private final JogadorService jogadorService;
    private final JogadorRepository jogadorRepository;

    public TimeService(TimeRepository timeRepository, TimeMapper timeMapper, JogoRepository jogoRepository, JogadorService jogadorService, JogadorRepository jogadorRepository) {

        this.timeRepository = timeRepository;
        this.timeMapper = timeMapper;
        this.jogoRepository = jogoRepository;
        this.jogadorService = jogadorService;
        this.jogadorRepository = jogadorRepository;
    }

    public Optional<TimeProjectionDTO> findById(long id) {
        Optional<Time> time = timeRepository.findTimeWithJogadoresAndJogosById(id);
        return time.map(timeMapper::toTimeProjectionDTO);
    }

    public void deleteEntity(Long id) {
        timeRepository.deleteById(id);
    }

    public Time newEntity(TimeCreateDTO dto) {
        Time entity = timeMapper.toEntity(dto);

        if (dto.jogoId() == null || dto.jogoId().isEmpty()) {
            throw new IllegalArgumentException("Lista de jogos não pode ser vazia.");
        }


        for (Long id : dto.jogoId()) {
            JogoEntity jogo = jogoRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Jogo não encontrada com o id: " + id));
            entity.getJogos().add(jogo);
        }

        return timeRepository.save(entity);
    }

    @Transactional
    public Time updateEntity(@PathVariable Long id, @RequestBody @Valid TimeUpdateDTO dto){
        Optional<Time> optionalEntity = timeRepository.findById(id);
        if (optionalEntity.isPresent()) {
            Time entity = optionalEntity.get();
            entity.setNome(dto.nome());
            entity.setDescricao(dto.descricao());
            entity.setFotoPath(dto.foto_path());

            List<JogoEntity> jogosAtuais = new ArrayList<>(entity.getJogos());
            List<Long> novaJogoIds = dto.jogoId();

            jogosAtuais.removeIf(jogo -> !novaJogoIds.contains(jogo.getId()));
            entity.getJogos().clear();
            entity.getJogos().addAll(jogosAtuais);

            for (Long jogoId : novaJogoIds) {
                JogoEntity jogo = jogoRepository.findById(jogoId)
                        .orElseThrow(() -> new EntityNotFoundException("Jogo não encontrado com o id: " + jogoId));
                entity.getJogos().add(jogo);
            }

            jogadorService.atualizarJogadoresDoTime(entity, dto.jogadoresId());


            return timeRepository.save(entity);
        } else {
            throw new RuntimeException("Id de time não encontrado");
        }


    }

    public ResponseEntity<BasePagination<TimeListAllDTO>> listAll(int page, int size, TimeFilter filter, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page, size);

        Specification<Time> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.nome() != null && !filter.nome().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + filter.nome().toLowerCase() + "%"));
            }

            if (filter.jogo() != null && !filter.jogo().isEmpty()) {
                Join<Time, JogoEntity> jogoJoin = root.join("jogos");
                predicates.add(criteriaBuilder.equal(jogoJoin.get("jogo"), filter.jogo()));
            }

            if (filter.liderTime() != null && !filter.liderTime().isBlank()) {
                Join<Time, Jogador> jogadorJoin = root.join("jogadores");

                Predicate nomePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(jogadorJoin.get("nome")),
                        "%" + filter.liderTime().toLowerCase() + "%");
                Predicate liderPredicate = criteriaBuilder.isTrue(jogadorJoin.get("liderTime"));
                predicates.add(criteriaBuilder.and(nomePredicate, liderPredicate));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Page<Time> times = timeRepository.findAll(spec, pageable);

        List<TimeListAllDTO> timeDtos = times.stream().map(time -> {
            List<String> lideresNomes = jogadorRepository.findLideresDeTimeByTimeId(time.getId())
                    .stream()
                    .map(Jogador::getNome)
                    .collect(Collectors.toList());

            int num_jogadores = jogadorRepository.findNumeroJogadoresByTimeId(time.getId());

            return timeMapper.toTimeListAllDTO(time, lideresNomes , num_jogadores);
        }).collect(Collectors.toList());

        Page<TimeListAllDTO> dtoPage = new PageImpl<>(timeDtos, pageable, times.getTotalElements());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());

        BasePagination<TimeListAllDTO> basePagination = new BasePagination<>(dtoPage, uriBuilder);

        return ResponseEntity.ok(basePagination);
    }


}