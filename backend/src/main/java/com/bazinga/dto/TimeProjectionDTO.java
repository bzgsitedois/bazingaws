package com.bazinga.dto;

import java.util.List;

public record TimeProjectionDTO(
        Long id,
        String nome,
        String descricao,
        String foto_path,
        List<Long> jogadoresId,
        List<Enum> categorias
    ) {

}
