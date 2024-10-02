package com.bazinga.mapper;


import com.bazinga.dto.JogadorProjectionDTO;
import com.bazinga.dto.TimeDTOs.TimeProjectionDTO;
import com.bazinga.entity.CategoriaEntity;
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
    JogadorProjectionDTO toJogadorProjectionDTO(Jogador jogador);


    default List<Enum> mapClasses(Set<ClasseTFEntity> classes) {
        return classes.stream()
                .map(ClasseTFEntity::getClasse)
                .collect(Collectors.toList());
    }
}
