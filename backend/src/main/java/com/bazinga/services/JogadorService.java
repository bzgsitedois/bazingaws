package com.bazinga.services;

import com.bazinga.bases.BasePagination;
import com.bazinga.dto.JogadorDTOs.*;
import com.bazinga.entity.ClasseTFEntity;
import com.bazinga.entity.Jogador;
import com.bazinga.entity.Time;
import com.bazinga.exception.JogadorNaoEncontradoException;
import com.bazinga.mapper.JogadorMapper;
import com.bazinga.repository.ClasseRepository;
import com.bazinga.repository.JogadorRepository;
import com.bazinga.repository.TimeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @Value("${fileJogador.upload-dir}")
    private String fotoDir;


    public Jogador getJogadorAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof Jogador)) {
            throw new RuntimeException("Usuário não autenticado.");
        }
        return (Jogador) authentication.getPrincipal();
    }

    public String getFotoPathPorJogador(Jogador jogador) {
        return this.fotoDir + jogador.getId() + ".jpg";
    }


    public void uploadFoto(MultipartFile foto) {
        try {
            Jogador jogador = getJogadorAutenticado();

            String fotoPath = getFotoPathPorJogador(jogador);

            Files.createDirectories(Paths.get(fotoDir));

            Files.write(Paths.get(fotoPath), foto.getBytes());

            jogador.setFotoPath(fotoPath);
        } catch (IOException e) {
            e.getLocalizedMessage();
        }
    }


    public String retornaPathFoto(Long id) {

        var jogador = jogadorRepository.findById(id).orElseThrow(JogadorNaoEncontradoException::new);

        Path path = Paths.get(getFotoPathPorJogador(jogador));

        if (!Files.exists(path)) {
            throw new RuntimeException("Foto não encontrada: " + fotoDir);
        }

        return jogador.getFotoPath();
    }

    public void removerJogadoresDoTime(Long timeId, List<Long> idsJogadoresParaRemover) {
        Jogador jogadorLogado = getJogadorAutenticado();


        if (jogadorLogado.getTime() == null) {
            throw new RuntimeException("Você não pertence a um time.");
        }

        if (!Boolean.TRUE.equals(jogadorLogado.getLiderTime())) {
            throw new RuntimeException("Apenas o líder do time pode definir outro jogador.");
        }

       timeRepository.findById(timeId).orElseThrow(() -> new EntityNotFoundException("Time não encontrado"));

        List<Jogador> jogadoresParaRemover = jogadorRepository.findAllById(idsJogadoresParaRemover);

        if (!jogadoresParaRemover.isEmpty()) {
            jogadoresParaRemover.forEach(jogador -> jogador.setTime(null));
            jogadorRepository.saveAll(jogadoresParaRemover);
        }
    }


    public void adicionarJogadoresAoTime(Long timeId, List<Long> idsJogadoresParaAdicionar) {

        Jogador jogadorLogado = getJogadorAutenticado();


        if (jogadorLogado.getTime() == null) {
            throw new RuntimeException("Você não pertence a um time.");
        }


        if (!Boolean.TRUE.equals(jogadorLogado.getLiderTime())) {
            throw new RuntimeException("Apenas o líder do time pode definir outro jogador.");
        }

        Time time = timeRepository.findById(timeId)
                .orElseThrow(() -> new EntityNotFoundException("Time não encontrado"));

        List<Jogador> jogadoresParaAdicionar = jogadorRepository.findAllById(idsJogadoresParaAdicionar);

        if (jogadoresParaAdicionar.get(0).getTime() != null) {
            throw new RuntimeException("Jogador "+jogadoresParaAdicionar.get(0).getNome()+" ja tem time.");
        }
        if (!jogadoresParaAdicionar.isEmpty()) {
            jogadoresParaAdicionar.forEach(jogador -> jogador.setTime(time));
            jogadorRepository.saveAll(jogadoresParaAdicionar);
        }
    }


    public List<JogadorSemTimeDTO> buscarJogadoresSemTime() {
        List<Object[]> resultados = jogadorRepository.findJogadoresSemTime();
        if (resultados.isEmpty()) {
            throw new JogadorNaoEncontradoException();
        }
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
        if (jogador.isEmpty()) {
            throw new JogadorNaoEncontradoException();
        }
        return jogador.map(jogadorMapper::toJogadorProjectionDTO);
    }

    public Jogador newEntity(JogadorCreateDTO dto) {
        Jogador entity = jogadorMapper.toEntity(dto);

        for (Long id : dto.classesId()) {
            ClasseTFEntity classe = classeRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Jogo não encontrada com o id: " + id));
            entity.getClasses().add(classe);
        }
        entity.setLiderTime(false);
        entity.setSenha(passwordEncoder.encode(dto.senha()));

        return jogadorRepository.save(entity);
    }


    public void definirNovoLider(Long jogadorId) {
        Jogador jogadorLogado = getJogadorAutenticado();


        if (jogadorLogado.getTime() == null) {
            throw new RuntimeException("Você não pertence a um time.");
        }

        if (!Boolean.TRUE.equals(jogadorLogado.getLiderTime())) {
            throw new RuntimeException("Apenas o líder do time pode definir outro jogador como líder.");
        }

        Jogador novoLider = jogadorRepository.findById(jogadorId)
                .orElseThrow(JogadorNaoEncontradoException::new);

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
        Jogador jogadorLogado = getJogadorAutenticado();


        if (jogadorLogado.getTime() == null) {
            throw new RuntimeException("Você não pertence a um time.");
        }

        if (!Boolean.TRUE.equals(jogadorLogado.getLiderTime())) {
            throw new RuntimeException("Apenas o líder do time pode retirar o cargo lider de outro jogador como líder.");
        }

        Jogador antigoLider = jogadorRepository.findById(jogadorId)
                .orElseThrow(JogadorNaoEncontradoException::new);

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
                .orElseThrow(JogadorNaoEncontradoException::new);

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
