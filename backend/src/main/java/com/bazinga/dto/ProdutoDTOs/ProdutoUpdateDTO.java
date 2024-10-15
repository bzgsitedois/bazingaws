package com.bazinga.dto.ProdutoDTOs;

import java.util.List;

public record ProdutoUpdateDTO(
        String nome,
        String logo,
        double preco,
        double desconto,
        double frete,
        String materiais,
        List<Enum> tamanho,
        String fotoPath,
        int quantidade
) {
}
