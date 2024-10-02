package com.bazinga.mapper;


import com.bazinga.dto.JogadorDTOs.JogadorCreateDTO;
import com.bazinga.dto.JogadorDTOs.JogadorListAllDTO;
import com.bazinga.dto.JogadorDTOs.JogadorProjectionDTO;
import com.bazinga.dto.TimeDTOs.TimeCreateDTO;
import com.bazinga.dto.TimeDTOs.TimeListAllDTO;
import com.bazinga.entity.ClasseTFEntity;
import com.bazinga.entity.Jogador;
import com.bazinga.entity.Time;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring")
public interface JogadorMapper {
    @Mapping(source = "classes", target = "classes")
    @Mapping(source = "time.id", target = "time_id")
    JogadorProjectionDTO toJogadorProjectionDTO(Jogador jogador);

    @Mapping(target = "classes", ignore = true)
    Jogador toEntity(JogadorCreateDTO dto);

    default List<Enum> mapClasses(Set<ClasseTFEntity> classes) {
        return classes.stream()
                .map(ClasseTFEntity::getClasse)
                .collect(Collectors.toList());
    }

    default JogadorListAllDTO toTimeListAllDTO(Jogador jogador , String timeNome , List<Long> classesIds) {
        return new JogadorListAllDTO(
                jogador.getId(),
                jogador.getNome(),
                jogador.getFotoPath(),
                classesIds,
                timeNome,
                jogador.getLiderTime()
        );
    }}
