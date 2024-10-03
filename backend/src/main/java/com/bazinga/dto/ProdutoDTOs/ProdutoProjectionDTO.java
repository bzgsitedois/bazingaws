package com.bazinga.dto.ProdutoDTOs;

import com.bazinga.entity.enums.Tamanho;

import java.util.List;

public record ProdutoProjectionDTO (
    Long id,
    String nome,
    String logo,
    double preco,
    double desconto,
    double frete,
    String materiais,
    List<Enum> tamanho,
    String fotoPath,
    int quantidade
)
{}