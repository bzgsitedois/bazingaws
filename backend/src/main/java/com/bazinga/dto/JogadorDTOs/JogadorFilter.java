package com.bazinga.dto.JogadorDTOs;

import java.util.List;

public record JogadorFilter(
        String nome,
        List<Long> classeIds,
        String timeNome,
        Boolean liderTime
) {
}
