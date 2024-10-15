package com.bazinga.dto.ProdutoDTOs;

public record ProdutoListAllDTO(
        Long id,
        String nome,
        String fotoPath,
        double preco,
        double desconto,
        int quantidade,
        String logo,
        String materiais
) {
}
