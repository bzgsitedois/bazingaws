package com.bazinga.dto.JogadorDTOs;

import java.util.List;

public record JogadorListAllDTO(
        Long id,
        String nome,
        String fotoPath,
        List<Long> classeIds,
        Long timeId,
        String timeNome,
        Boolean liderTime
) {
}
