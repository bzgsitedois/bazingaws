package com.bazinga.dto;

import com.bazinga.entity.Jogador;

import java.util.List;

public record TimeListAllDTO(
        Long id,
        String nome,
        String descricao,
        String foto_path,
        List<String> lideres) {
}
