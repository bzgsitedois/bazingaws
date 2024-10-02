package com.bazinga.dto;

import java.util.List;

public record JogadorProjectionDTO(
        Long id,
        String nome,
       String fotoPath,
        List<Enum> classes,
        Long time_id,
        Boolean liderTime
) {
}
