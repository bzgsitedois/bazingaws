package com.bazinga.mapper;

import com.bazinga.dto.TimeDTOs.TimeCreateDTO;
import com.bazinga.dto.TimeDTOs.TimeProjectionDTO;
import com.bazinga.entity.Time;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-03T17:39:43-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Amazon.com Inc.)"
)
@Component
public class TimeMapperImpl implements TimeMapper {

    @Override
    public TimeProjectionDTO toTimeProjectionDTO(Time time) {
        if ( time == null ) {
            return null;
        }

        List<Long> jogadoresId = null;
        List<Enum> jogos = null;
        Long id = null;
        String nome = null;
        String descricao = null;

        jogadoresId = mapJogadoresToIds( time.getJogadores() );
        jogos = mapJogos( time.getJogos() );
        id = time.getId();
        nome = time.getNome();
        descricao = time.getDescricao();

        String foto_path = null;

        TimeProjectionDTO timeProjectionDTO = new TimeProjectionDTO( id, nome, descricao, foto_path, jogadoresId, jogos );

        return timeProjectionDTO;
    }

    @Override
    public Time toEntity(TimeCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Time time = new Time();

        time.setNome( dto.nome() );
        time.setDescricao( dto.descricao() );
        time.setId( dto.id() );

        return time;
    }
}
