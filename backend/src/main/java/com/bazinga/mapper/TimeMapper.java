package com.bazinga.mapper;

import com.bazinga.dto.TimeCreateDTO;
import com.bazinga.dto.TimeProjectionDTO;
import com.bazinga.entity.CategoriaEntity;
import com.bazinga.entity.Jogador;
import com.bazinga.entity.Time;
import com.bazinga.repository.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring")
public interface TimeMapper {


    @Mapping(source = "jogadores", target = "jogadoresId")
    @Mapping(source = "categorias", target = "categorias")
    TimeProjectionDTO toTimeProjectionDTO(Time time);

    default List<Long> mapJogadoresToIds(List<Jogador> jogadores) {
        return jogadores.stream()
                .map(Jogador::getId)
                .collect(Collectors.toList());
    }

    default List<Enum> mapCategorias(Set<CategoriaEntity> categorias) {
        return categorias.stream()
                .map(CategoriaEntity::getCategoria)
                .collect(Collectors.toList());
    }


    @Mapping(target = "categorias", ignore = true)
    Time toEntity(TimeCreateDTO dto);
}
