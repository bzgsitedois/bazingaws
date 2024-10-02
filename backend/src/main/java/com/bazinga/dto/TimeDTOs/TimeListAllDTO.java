package com.bazinga.dto.TimeDTOs;

import java.util.List;

public record TimeListAllDTO(
        Long id,
        String nome,
        String descricao,
        String foto_path,
        List<String> lideres,
        int num_jogadores) {
}
