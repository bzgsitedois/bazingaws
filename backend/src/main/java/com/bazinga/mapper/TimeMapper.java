package com.bazinga.mapper;

import com.bazinga.dto.TimeDTOs.TimeCreateDTO;
import com.bazinga.dto.TimeDTOs.TimeListAllDTO;
import com.bazinga.dto.TimeDTOs.TimeProjectionDTO;
import com.bazinga.entity.JogoEntity;
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
public interface TimeMapper {

    @Mapping(source = "jogadores", target = "jogadoresId")
    @Mapping(source = "jogos", target = "jogos")
    TimeProjectionDTO toTimeProjectionDTO(Time time);

    default List<Long> mapJogadoresToIds(List<Jogador> jogadores) {
        return jogadores.stream()
                .map(Jogador::getId)
                .collect(Collectors.toList());
    }

    default List<Enum> mapJogos(Set<JogoEntity> jogos) {
        return jogos.stream()
                .map(JogoEntity::getJogo)
                .collect(Collectors.toList());
    }

    @Mapping(target = "jogos", ignore = true)
    Time toEntity(TimeCreateDTO dto);

    default TimeListAllDTO toTimeListAllDTO(Time time, List<String> lideresNomes , int num_jogadores) {
        return new TimeListAllDTO(
                time.getId(),
                time.getNome(),
                time.getDescricao(),
                time.getFotoPath(),
                lideresNomes,
                num_jogadores
        );
    }
}
