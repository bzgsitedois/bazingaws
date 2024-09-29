package com.bazinga.mapper;

import com.bazinga.dto.TimeProjectionDTO;
import com.bazinga.entity.Time;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-29T18:18:44-0300",
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
        List<Enum> categorias = null;
        Long id = null;
        String nome = null;
        String descricao = null;

        jogadoresId = mapJogadoresToIds( time.getJogadores() );
        categorias = mapCategorias( time.getCategorias() );
        id = time.getId();
        nome = time.getNome();
        descricao = time.getDescricao();

        TimeProjectionDTO timeProjectionDTO = new TimeProjectionDTO( id, nome, descricao, jogadoresId, categorias );

        return timeProjectionDTO;
    }
}
