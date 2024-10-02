package com.bazinga.services;

import com.bazinga.bases.BasePagination;
import com.bazinga.dto.JogadorDTOs.*;
import com.bazinga.dto.TimeDTOs.TimeCreateDTO;
import com.bazinga.dto.TimeDTOs.TimeFilter;
import com.bazinga.dto.TimeDTOs.TimeListAllDTO;
import com.bazinga.entity.CategoriaEntity;
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

    public JogadorService(JogadorRepository jogadorRepository, JogadorMapper jogadorMapper, ClasseRepository classeRepository, TimeRepository timeRepository) {
        this.jogadorRepository = jogadorRepository;
        this.jogadorMapper = jogadorMapper;
        this.classeRepository = classeRepository;
        this.timeRepository = timeRepository;
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

    public Optional<JogadorProjectionDTO> findById(long id) {
        Optional<Jogador> jogador = jogadorRepository.findJogadoresWithClassesById(id);
        return jogador.map(jogadorMapper::toJogadorProjectionDTO);
    }

    public Jogador newEntity(JogadorCreateDTO dto) {
        Jogador entity = jogadorMapper.toEntity(dto);

        for (Long id : dto.classesId()) {
            ClasseTFEntity classe = classeRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com o id: " + id));
            entity.getClasses().add(classe);
        }

        return jogadorRepository.save(entity);
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
                    .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com o id: " + classeId));
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
