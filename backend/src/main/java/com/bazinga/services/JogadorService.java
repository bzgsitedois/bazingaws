package com.bazinga.services;

import com.bazinga.bases.BasePagination;
import com.bazinga.dto.JogadorDTOs.*;
import com.bazinga.entity.ClasseTFEntity;
import com.bazinga.entity.Jogador;
import com.bazinga.entity.Time;
import com.bazinga.mapper.JogadorMapper;
import com.bazinga.repository.ClasseRepository;
import com.bazinga.repository.JogadorRepository;
import com.bazinga.repository.TimeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JogadorService {

    private final JogadorRepository jogadorRepository;
    private final JogadorMapper jogadorMapper;
    private final ClasseRepository classeRepository;
    private final TimeRepository timeRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    public JogadorService(JogadorRepository jogadorRepository, JogadorMapper jogadorMapper, ClasseRepository classeRepository, TimeRepository timeRepository, BCryptPasswordEncoder passwordEncoder) {
        this.jogadorRepository = jogadorRepository;
        this.jogadorMapper = jogadorMapper;
        this.classeRepository = classeRepository;
        this.timeRepository = timeRepository;
        this.passwordEncoder = passwordEncoder;
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

    public Optional<JogadorProjectionDTO> findById(Long id) {
        Optional<Jogador> jogador = jogadorRepository.findJogadoresWithClassesById(id);
        return jogador.map(jogadorMapper::toJogadorProjectionDTO);
    }

    public Jogador newEntity(JogadorCreateDTO dto) {
        Jogador entity = jogadorMapper.toEntity(dto);

        for (Long id : dto.classesId()) {
            ClasseTFEntity classe = classeRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Jogo não encontrada com o id: " + id));
            entity.getClasses().add(classe);
        }
        entity.setSenha(passwordEncoder.encode(dto.senha()));

        return jogadorRepository.save(entity);
    }


    public void definirNovoLider(Long jogadorId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jogador jogadorLogado = (Jogador) authentication.getPrincipal();

        if (jogadorLogado.getTime() == null) {
            throw new RuntimeException("Você não pertence a um time.");
        }

        if (!Boolean.TRUE.equals(jogadorLogado.getLiderTime())) {
            throw new RuntimeException("Apenas o líder do time pode definir outro jogador como líder.");
        }

        Jogador novoLider = jogadorRepository.findById(jogadorId)
                .orElseThrow(() -> new RuntimeException("Jogador não encontrado."));

        if (!novoLider.getTime().getId().equals(jogadorLogado.getTime().getId())) {
            throw new RuntimeException("O jogador deve pertencer ao mesmo time para ser promovido a líder.");
        }

        Long quantidadeLideres = jogadorRepository.countByTimeIdAndLiderTimeTrue(jogadorLogado.getTime().getId());
        if (quantidadeLideres >= 2) {
            throw new RuntimeException("O time já possui 2 líderes.");
        }

        novoLider.setLiderTime(true);
        jogadorRepository.save(novoLider);
    }

    public void tirarCargoLider(Long jogadorId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jogador jogadorLogado = (Jogador) authentication.getPrincipal();

        if (jogadorLogado.getTime() == null) {
            throw new RuntimeException("Você não pertence a um time.");
        }

        if (!Boolean.TRUE.equals(jogadorLogado.getLiderTime())) {
            throw new RuntimeException("Apenas o líder do time pode retirar o cargo lider de outro jogador como líder.");
        }

        Jogador antigoLider = jogadorRepository.findById(jogadorId)
                .orElseThrow(() -> new RuntimeException("Jogador não encontrado."));

        if (!antigoLider.getTime().getId().equals(jogadorLogado.getTime().getId())) {
            throw new RuntimeException("O jogador deve pertencer ao mesmo time para ser despromovido a líder.");
        }

        Long quantidadeLideres = jogadorRepository.countByTimeIdAndLiderTimeTrue(jogadorLogado.getTime().getId());
        if (quantidadeLideres == 1) {
            throw new RuntimeException("O time deve ter no minimo um lider.");
        }

        antigoLider.setLiderTime(false);
        jogadorRepository.save(antigoLider);
    }

    public Jogador updateEntity(JogadorUpdateDTO dto , Long id){
        Jogador entity = jogadorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Jogador não encontrado com o id: " + id));

        entity.setNome(dto.nome());
        entity.setEmail(dto.email());
        entity.setFotoPath(dto.fotoPath());

        Set<ClasseTFEntity> novasClasses = new HashSet<>();
        for (Long classeId : dto.classesId()) {
            ClasseTFEntity classeTF = classeRepository.findById(classeId)
                    .orElseThrow(() -> new EntityNotFoundException("Jogo não encontrada com o id: " + classeId));
            novasClasses.add(classeTF);
        }
        entity.setClasses(novasClasses);

        return jogadorRepository.save(entity);

    }

    public void deleteEntity(Long id) {
        jogadorRepository.deleteById(id);
    }

    public ResponseEntity<BasePagination<JogadorListAllDTO>> listAll(int page, int size, JogadorFilter filter, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page, size);

        Specification<Jogador> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.nome() != null && !filter.nome().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + filter.nome().toLowerCase() + "%"));
            }

            if (filter.classeIds() != null && !filter.classeIds().isEmpty()) {
                Join<Jogador, ClasseTFEntity> classeJoin = root.join("classes");
                predicates.add(classeJoin.get("id").in(filter.classeIds()));
            }

            if (filter.liderTime() != null) {
                predicates.add(criteriaBuilder.equal(root.get("liderTime"), filter.liderTime()));
            }

            if (filter.timeNome() != null && !filter.timeNome().isEmpty()) {
                Join<Jogador, Time> timeJoin = root.join("time");
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(timeJoin.get("nome")), "%" + filter.timeNome().toLowerCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Page<Jogador> jogadores = jogadorRepository.findAll(spec, pageable);

        List<JogadorListAllDTO> jogadorDtos = jogadores.stream().map(jogador -> {
            String timeNome = timeRepository.findNomeDoTimeByJogadorId(jogador.getId());
            List<Long> classesId = classeRepository.findClassesIdsByJogadorId(jogador.getId());
            return jogadorMapper.toTimeListAllDTO(jogador, timeNome, classesId);
        }).collect(Collectors.toList());

        Page<JogadorListAllDTO> dtoPage = new PageImpl<>(jogadorDtos, pageable, jogadores.getTotalElements());
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
        BasePagination<JogadorListAllDTO> basePagination = new BasePagination<>(dtoPage, uriBuilder);

        return ResponseEntity.ok(basePagination);
    }



}
