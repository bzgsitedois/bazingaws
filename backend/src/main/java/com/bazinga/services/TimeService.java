package com.bazinga.services;


import com.bazinga.dto.TimeCreateDTO;
import com.bazinga.dto.TimeProjectionDTO;
import com.bazinga.dto.TimeUpdateDTO;
import com.bazinga.entity.CategoriaEntity;
import com.bazinga.entity.Time;
import com.bazinga.mapper.TimeMapper;
import com.bazinga.repository.CategoriaRepository;
import com.bazinga.repository.TimeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TimeService {
    private final TimeRepository timeRepository;
    private final TimeMapper timeMapper;
    private final CategoriaRepository categoriaRepository;
    private final JogadorService jogadorService;

    public TimeService(TimeRepository timeRepository, TimeMapper timeMapper, CategoriaRepository categoriaRepository, JogadorService jogadorService) {

        this.timeRepository = timeRepository;
        this.timeMapper = timeMapper;
        this.categoriaRepository = categoriaRepository;
        this.jogadorService = jogadorService;
    }

    public Optional<TimeProjectionDTO> findById(long id) {
        Optional<Time> time = timeRepository.findTimeWithJogadoresAndCategoriasById(id);
        return time.map(timeMapper::toTimeProjectionDTO);
    }

    public void deleteEntity(Long id) {
        timeRepository.deleteById(id);
    }

    public Time newEntity(TimeCreateDTO dto) {
        Time entity = timeMapper.toEntity(dto);

        if (dto.categoriaId() == null || dto.categoriaId().isEmpty()) {
            throw new IllegalArgumentException("Lista de categorias não pode ser vazia.");
        }


        for (Long id : dto.categoriaId()) {
            CategoriaEntity categoria = categoriaRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com o id: " + id));
            entity.getCategorias().add(categoria);
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

            List<CategoriaEntity> categoriasAtuais = new ArrayList<>(entity.getCategorias());
            List<Long> novaCategoriaIds = dto.categoriaId();

            categoriasAtuais.removeIf(categoria -> !novaCategoriaIds.contains(categoria.getId()));
            entity.getCategorias().clear();
            entity.getCategorias().addAll(categoriasAtuais);

            for (Long categoriaId : novaCategoriaIds) {
                CategoriaEntity categoria = categoriaRepository.findById(categoriaId)
                        .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com o id: " + categoriaId));
                entity.getCategorias().add(categoria);
            }

            jogadorService.atualizarJogadoresDoTime(entity, dto.jogadoresId());


            return timeRepository.save(entity);
        } else {
            throw new RuntimeException("Id de time não encontrado");
        }


    }
}