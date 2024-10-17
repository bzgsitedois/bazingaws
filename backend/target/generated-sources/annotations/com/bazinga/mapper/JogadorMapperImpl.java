package com.bazinga.mapper;

import com.bazinga.dto.JogadorDTOs.JogadorCreateDTO;
import com.bazinga.dto.JogadorDTOs.JogadorProjectionDTO;
import com.bazinga.entity.Jogador;
import com.bazinga.entity.Time;
import com.bazinga.entity.enums.Perfil;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-08T17:30:11-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Oracle Corporation)"
)
@Component
public class JogadorMapperImpl implements JogadorMapper {

    @Override
    public JogadorProjectionDTO toJogadorProjectionDTO(Jogador jogador) {
        if ( jogador == null ) {
            return null;
        }

        List<Enum> classes = null;
        Long time_id = null;
        Long id = null;
        String nome = null;
        String fotoPath = null;
        Boolean liderTime = null;

        classes = mapClasses( jogador.getClasses() );
        time_id = jogadorTimeId( jogador );
        id = jogador.getId();
        nome = jogador.getNome();
        fotoPath = jogador.getFotoPath();
        liderTime = jogador.getLiderTime();

        JogadorProjectionDTO jogadorProjectionDTO = new JogadorProjectionDTO( id, nome, fotoPath, classes, time_id, liderTime );

        return jogadorProjectionDTO;
    }

    @Override
    public Jogador toEntity(JogadorCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Jogador jogador = new Jogador();

        jogador.setId( dto.id() );
        jogador.setNome( dto.nome() );
        jogador.setEmail( dto.email() );
        jogador.setFotoPath( dto.fotoPath() );
        if ( dto.perfil() != null ) {
            jogador.setPerfil( Enum.valueOf( Perfil.class, dto.perfil() ) );
        }
        jogador.setLiderTime( dto.liderTime() );

        return jogador;
    }

    private Long jogadorTimeId(Jogador jogador) {
        if ( jogador == null ) {
            return null;
        }
        Time time = jogador.getTime();
        if ( time == null ) {
            return null;
        }
        Long id = time.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
